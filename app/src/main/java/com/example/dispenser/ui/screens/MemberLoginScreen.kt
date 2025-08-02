// 파일 최상단에 OptIn 선언
@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class
)

package com.example.dispenser.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dispenser.viewmodel.LoginViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.LaunchedEffect
import com.example.dispenser.data.local.TokenManager


/**
 * 회원 로그인 화면
 *
 * @param onLoginSuccess 로그인 성공 시 호출 (MemberHome 화면으로 네비게이트)
 * @param onBack 뒤로 가기 (Welcome 화면으로)
 * @param onHome 홈 아이콘 클릭 시 호출
 */
@Composable
fun MemberLoginScreen(
    onLoginSuccess: () -> Unit,
    onBack: () -> Unit,
    onHome: () -> Unit
) {
    val context = LocalContext.current
    val tokenManager: TokenManager = remember { TokenManager(context) }
    val viewModel = remember { LoginViewModel(tokenManager) }

    val loginSuccess = viewModel.loginSuccess.value
    val message = viewModel.loginMessage.value

    if (message != null) {
        LaunchedEffect(message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.loginMessage.value = null // 메시지 초기화
        }
    }

    if (loginSuccess) {
        // 로그인 성공하면 홈 화면으로 이동
        onLoginSuccess()
        viewModel.resetLoginSuccess() // 상태 초기화
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 필요하면 제목 넣기 */ },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    IconButton(onClick = onHome) {
                        Icon(Icons.Filled.Home, contentDescription = "홈으로")
                    }
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ){
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("회원 로그인", style = MaterialTheme.typography.headlineSmall)

                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("이메일") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("비밀번호") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {viewModel.login(email, password)},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                    ) {
                        Text("로그인", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

        }
    )
}
