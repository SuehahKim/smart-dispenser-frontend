// 파일 최상단에 OptIn 선언
@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class
)

package com.example.dispenser.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.offset

/**
 * 회원가입 화면
 *
 * @param onSignUpSuccess 회원가입 완료 시 호출 (→ MemberLogin 화면으로 네비게이트)
 * @param onBack 뒤로 가기 (Welcome 화면으로)
 * @param onHome 홈 아이콘 클릭 시 호출 (Welcome 화면으로)
 */
@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onBack: () -> Unit,
    onHome: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 필요 시 제목 넣기 */ },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.height(48.dp)) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            modifier = Modifier.height(32.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onHome, modifier = Modifier.height(48.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "홈으로",
                            modifier = Modifier.height(32.dp)
                        )
                    }
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)      // 화면 중앙 정렬
                        .offset(y = (-32).dp)
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(35.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "회원가입",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var confirmPassword by remember { mutableStateOf("") }

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

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("비밀번호 확인") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = onSignUpSuccess,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                    ) {
                        Text(
                            text = "회원가입",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    )
}
