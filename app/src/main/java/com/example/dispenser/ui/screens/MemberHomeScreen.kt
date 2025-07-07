package com.example.dispenser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 회원용 홈 화면 (임시)
 *
 * @param onLogout 로그아웃 콜백 (Welcome 으로 돌아감)
 */
@Composable
fun MemberHomeScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🏠 회원 전용 홈 (임시)", modifier = Modifier.padding(bottom = 24.dp))
        Button(onClick = onLogout) {
            Text("로그아웃")
        }
    }
}
