package com.example.dispenser.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.example.dispenser.ui.screens.*
import com.example.dispenser.data.local.InstallIdManager
import com.example.dispenser.viewmodel.LoginViewModel

// ✅ 기존처럼 이 파일에 Screen을 둔다 (인자 없는 제조 라우트)
sealed class Screen(val route: String) {
    object Welcome     : Screen("welcome")
    object MemberLogin : Screen("member_login")
    object SignUp      : Screen("sign_up")
    object MemberHome  : Screen("member_home")
    object GuestHome   : Screen("guest_home")
    object Favorite    : Screen("favorite")
    object History     : Screen("history")
    object StockCheck  : Screen("stock_check")
    object DeviceConnect : Screen("device_connect")
    object Manufacturing : Screen("manufacturing")                 // ⬅️ 인자 없음
    object ManufacturingComplete : Screen("manufacturing_complete")
}

@Composable
fun NavGraph(startDestination: String = Screen.Welcome.route) {
    val navController = rememberNavController()

    NavHost(navController, startDestination) {

        // 1) 첫 화면
        composable(Screen.Welcome.route) {
            val loginViewModel: LoginViewModel = viewModel()
            val context = LocalContext.current
            val scope = rememberCoroutineScope()
            val loginSuccess by loginViewModel.loginSuccess

            WelcomeScreen(
                onMemberLogin = { navController.navigate(Screen.MemberLogin.route) },
                onGuestLogin  = {
                    scope.launch {
                        val uuid = InstallIdManager(context).getOrCreate()
                        loginViewModel.guestLogin(uuid)
                    }
                },
                onSignUp      = { navController.navigate(Screen.SignUp.route) }
            )

            if (loginSuccess) {
                LaunchedEffect(Unit) {
                    loginViewModel.resetLoginSuccess()
                    navController.navigate(Screen.GuestHome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }

        // 2) 회원 로그인
        composable(Screen.MemberLogin.route) {
            MemberLoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.MemberHome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onBack = { navController.popBackStack() },
                onHome = { navController.popBackStack(Screen.Welcome.route,false) }
            )
        }

        // 3) 회원가입
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Screen.MemberLogin.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onBack = { navController.popBackStack() },
                onHome = { navController.popBackStack(Screen.Welcome.route, false) }
            )
        }

        // 4) 회원 홈
        composable(Screen.MemberHome.route) {
            MemberHomeScreen(
                onBack = { navController.popBackStack() },
                onHome = { navController.popBackStack(Screen.Welcome.route, false) },
                onFavorites = { navController.navigate(Screen.Favorite.route) },
                onHistory    = { navController.navigate(Screen.History.route) },
                onStockCheck = { navController.navigate(Screen.StockCheck.route) },
                onConnectDevice = { navController.navigate(Screen.DeviceConnect.route) }
            )
        }

        // 5) 게스트 홈
        composable(Screen.GuestHome.route) {
            GuestHomeScreen(
                onLogout = { navController.popBackStack(Screen.Welcome.route, false) },
                onStockCheck = { navController.navigate(Screen.StockCheck.route) },
                onConnectDevice = { navController.navigate(Screen.DeviceConnect.route) }
            )
        }

        // 6) 기기 연결(QR)
        composable(Screen.DeviceConnect.route) {
            QRScanScreen(
                onScanSuccess = { navController.navigate(Screen.Manufacturing.route) }, // ⬅️ 인자 없이 이동
                onBack = { navController.popBackStack() }
            )
        }

        // 7) 즐겨찾기
        composable(Screen.Favorite.route) {
            FavoriteScreen(
                navController = navController,
                onBack = { navController.popBackStack() },
                onHome = { navController.popBackStack(Screen.Welcome.route, false) }
            )
        }

        // 8) 사용 이력
        composable(Screen.History.route) {
            HistoryScreen(
                navController = navController,
                onBack = { navController.popBackStack() },
                onHome = { navController.popBackStack(Screen.Welcome.route, false) }
            )
        }

        // 9) 잔량
        composable(Screen.StockCheck.route) {
            StockCheckScreen(
                onBack = { navController.popBackStack() },
                onHome = { navController.popBackStack(Screen.Welcome.route, false) }
            )
        }

        // 10) 제조중 — ⬅️ arguments 제거
        composable(Screen.Manufacturing.route) {
            ManufacturingScreen(
                navController = navController,
                onBack = { navController.popBackStack() },
                onHome = { navController.navigate(Screen.MemberHome.route) { launchSingleTop = true } }
                // manufactureId는 기본값(null) → 4초 로컬 타이머 동작
            )
        }

        // 11) 제조 완료
        composable(Screen.ManufacturingComplete.route) {
            ManufacturingCompleteScreen(
                navController = navController,
                onBack = { navController.popBackStack() },
                onHome = {
                    navController.navigate(Screen.MemberHome.route) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
