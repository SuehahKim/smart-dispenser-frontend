package com.example.dispenser.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dispenser.ui.screens.*

sealed class Screen(val route: String) {
    object Welcome     : Screen("welcome")
    object MemberLogin : Screen("member_login")
    object GuestLogin  : Screen("guest_login")
    object SignUp      : Screen("sign_up")
    object MemberHome  : Screen("member_home")
    object GuestHome   : Screen("guest_home")
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
                onBack = { navController.popBackStack() }
            )
        }

        // 3) 비회원 로그인 → 바로 GuestHome
        composable(Screen.GuestLogin.route) {
            GuestLoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.GuestHome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onBack = { navController.popBackStack() }
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
                onBack = { navController.popBackStack() }
            )
        }

        // 5) 회원 전용 홈 (임시)
        composable(Screen.MemberHome.route) {
            MemberHomeScreen(onLogout = {
                navController.popBackStack(Screen.Welcome.route, false)
            })
        }

        // 6) 게스트 전용 홈 (임시)
        composable(Screen.GuestHome.route) {
            GuestHomeScreen(onLogout = {
                navController.popBackStack(Screen.Welcome.route, false)
            })
        }
    }
}
