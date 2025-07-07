package com.example.dispenser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 첫 화면: 회원 로그인, 비회원 로그인, 회원가입 버튼 제공
 *
 * @param onMemberLogin   회원 로그인 화면으로 이동하는 콜백
 * @param onGuestLogin    비회원 로그인 화면으로 이동하는 콜백
 * @param onSignUp        회원가입 화면으로 이동하는 콜백
 */
@Composable
fun WelcomeScreen(
    onMemberLogin: () -> Unit,
    onGuestLogin : () -> Unit,
    onSignUp     : () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "양념 디스펜서 앱",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Button(
            onClick = onMemberLogin,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("회원 로그인")
        }

        Button(
            onClick = onGuestLogin,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("비회원 로그인")
        }

        Button(
            onClick = onSignUp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("회원가입")
        }
    }
}
