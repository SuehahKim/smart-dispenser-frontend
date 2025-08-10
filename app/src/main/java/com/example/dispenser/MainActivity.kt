package com.example.dispenser

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.dispenser.data.local.TokenHolder
import com.example.dispenser.data.local.TokenManager
import com.example.dispenser.navigation.NavGraph
import com.example.dispenser.ui.theme.DispenserTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DispenserTheme {
                NavGraph()
            }
        }

        // ✅ 앱 시작 시 저장된 토큰 확인 + TokenHolder에 올리기
        val tokenManager = TokenManager(applicationContext)
        lifecycleScope.launch {
            val savedAccess = tokenManager.getAccessToken()
            val savedRefresh = tokenManager.getRefreshToken()

            // 인터셉터에서 바로 쓰도록 메모리에 탑재
            TokenHolder.accessToken = savedAccess

            // Logcat으로 확인 (Tag: TokenCheck)
            Log.d("TokenCheck", "Saved Access Token = ${savedAccess?.take(16)}")
            Log.d("TokenCheck", "Saved Refresh Token = ${savedRefresh?.take(16)}")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DispenserTheme {
        Greeting("Android")
    }
}
