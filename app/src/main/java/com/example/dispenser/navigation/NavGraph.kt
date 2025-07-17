package com.example.dispenser.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dispenser.ui.screens.*

sealed class Screen(val route: String) {
    object Welcome     : Screen("welcome")
    object MemberLogin : Screen("member_login")
    object SignUp      : Screen("sign_up")
    object MemberHome  : Screen("member_home")
    object GuestHome   : Screen("guest_home")
    object Favorite   : Screen("favorite")
    object History    : Screen("history")
    object StockCheck  : Screen("stock_check")
}

@Composable
fun NavGraph(startDestination: String = Screen.Welcome.route) {
    val navController = rememberNavController()

    NavHost(navController, startDestination) {
        // 1) 첫 화면
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onMemberLogin = { navController.navigate(Screen.MemberLogin.route) },
                onGuestLogin  = { navController.navigate(Screen.GuestHome.route) },
                onSignUp      = { navController.navigate(Screen.SignUp.route) }
            )
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
                onBack = {
                    navController.popBackStack()
                },
                onHome = {
                    navController.popBackStack(Screen.Welcome.route,false)
                }

            )
        }



        // 4) 회원가입 → 회원 로그인 화면으로
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

        // 5) 회원 전용 홈
        composable(Screen.MemberHome.route) {
            MemberHomeScreen(
                onBack = { navController.popBackStack() },
                onHome = { navController.popBackStack(Screen.Welcome.route, false) },
                onFavorites = {navController.navigate(Screen.Favorite.route) },
                onHistory    = { navController.navigate(Screen.History.route) },
                onStockCheck = { navController.navigate(Screen.StockCheck.route) } // 예: 재고 확인
            )
        }

        // 6) 게스트 전용 홈
        composable(Screen.GuestHome.route) {
            GuestHomeScreen(onLogout = {
                navController.popBackStack(Screen.Welcome.route, false)
            })
        }

        //즐겨찾기
        composable(Screen.Favorite.route) {
            FavoriteScreen(
                onBack = { navController.popBackStack() },
                onHome = { navController.popBackStack(Screen.Welcome.route, false) }
            )
        }

        //사용이력
        composable(Screen.History.route) {
            HistoryScreen(
                onBack = { navController.popBackStack() },
                onHome = { navController.popBackStack(Screen.Welcome.route, false) }
            )
        }

        //잔량확인
        composable(Screen.StockCheck.route) {
            StockCheckScreen(
                onBack = { navController.popBackStack() },
                onHome = { navController.popBackStack(Screen.Welcome.route, false) }
            )
        }
    }
}
