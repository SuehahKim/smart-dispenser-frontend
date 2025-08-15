package com.example.dispenser.data.api

import com.example.dispenser.data.local.TokenHolder
import com.example.dispenser.data.local.TokenManager
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * 401(Unauthorized) 발생 시 자동으로 refresh 후, 원요청을 새 access 토큰으로 재시도.
 * - refresh 실패 시 TokenManager.clear()로 강제 로그아웃 상태로 전환.
 * - 중복 refresh 방지를 위해 mutex 사용.
 */
class TokenAuthenticator(
    private val tokenManager: TokenManager,
    private val refreshApi: AuthRefreshApi
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        // 무한루프 방지: 재시도 2회 이상이면 중단
        if (responseCount(response) >= 2) return null

        return runBlocking {
            mutex.withLock {
                val currentAccess = tokenManager.getAccessToken()
                val requestAccess = response.request.header("Authorization")
                    ?.removePrefix("Bearer ")?.trim()

                // 이미 다른 스레드가 갱신했다면, 갱신된 토큰으로 즉시 재시도
                if (!currentAccess.isNullOrBlank() && currentAccess != requestAccess) {
                    return@runBlocking newRequestWithAccessToken(response.request, currentAccess)
                }

                val refresh = tokenManager.getRefreshToken() ?: return@runBlocking null
                try {
                    val newTokens = refreshApi.refresh(RefreshRequest(refresh))

                    // 저장(서버가 새 refresh를 주면 교체, 안 주면 기존 유지해도 OK)
                    tokenManager.saveTokens(
                        newTokens.accessToken,
                        newTokens.refreshToken ?: refresh
                    )
                    // 메모리 보조 캐시도 갱신(네 코드에서 쓰고 있으니 유지)
                    TokenHolder.accessToken = newTokens.accessToken

                    return@runBlocking newRequestWithAccessToken(response.request, newTokens.accessToken)
                } catch (e: Exception) {
                    // refresh 실패 → 강제 로그아웃
                    tokenManager.clear()
                    TokenHolder.accessToken = null
                    null
                }
            }
        }
    }

    private fun newRequestWithAccessToken(old: Request, access: String): Request {
        return old.newBuilder()
            .header("Authorization", "Bearer $access")
            .build()
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
