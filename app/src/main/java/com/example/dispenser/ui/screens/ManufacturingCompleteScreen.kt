@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.dispenser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dispenser.navigation.Screen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home

@Composable
fun ManufacturingCompleteScreen(
    navController: NavController,
    onBack: () -> Unit,
    onHome: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.MemberHome.route) {
                            popUpTo(0)
                        }
                    }) {
                        Icon(Icons.Default.Home, contentDescription = "홈")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(80.dp))

            Text("제조완료", fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(80.dp))

            LinearProgressIndicator(
                progress = 1f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.LightGray
            )
            Spacer(Modifier.height(80.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screen.StockCheck.route + "?origin=manufacturing") {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("잔량확인", color = Color.White)
                }

                Button(
                    onClick = { /* 즐겨찾기 추가 로직 */ },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("즐겨찾기에 추가", color = Color.White)
                }

                Button(
                    onClick = { navController.navigate(Screen.MemberHome.route) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("다시 만들기", color = Color.White)
                }
            }
        }
    }
}
