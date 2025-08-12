package com.example.dispenser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        // 버튼 사이 간격을 24dp로 넉넉히 띄워줍니다.
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 제목 글자 크기를 36sp로 키우고, 볼드 처리
        Text(
            text = "스마트 디스펜서",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        )

        // 버튼 높이를 56dp로 늘리고, 글자 크기는 20sp로 키웁니다.
        Button(
            onClick = onMemberLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("회원 로그인", fontSize = 20.sp)
        }

        Button(
            onClick = onGuestLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("비회원 로그인", fontSize = 20.sp)
        }

        Button(
            onClick = onSignUp,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("회원가입", fontSize = 20.sp)
        }
    }
}
