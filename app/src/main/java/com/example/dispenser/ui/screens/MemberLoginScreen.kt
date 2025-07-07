package com.example.dispenser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

/**
 * 회원 로그인 화면
 *
 * @param onLoginSuccess 로그인 성공 시 호출 (Home 화면으로 네비게이트)
 * @param onBack 뒤로 가기
 */
@Composable
fun MemberLoginScreen(
    onLoginSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "회원 로그인", modifier = Modifier.padding(bottom = 24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("이메일") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // TODO: 실제 로그인 로직 → 성공하면 onLoginSuccess()
                onLoginSuccess()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("로그인")
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("뒤로가기")
        }
    }
}
