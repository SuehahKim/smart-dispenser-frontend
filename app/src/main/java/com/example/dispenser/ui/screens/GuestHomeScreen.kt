package com.example.dispenser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 게스트용 홈 화면 (임시)
 *
 * @param onLogout 뒤로 돌아가는 콜백 (Welcome)
 */
@Composable
fun GuestHomeScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🌐 게스트 전용 홈 (임시)", modifier = Modifier.padding(bottom = 24.dp))
        Button(onClick = onLogout) {
            Text("뒤로")
        }
    }
}
