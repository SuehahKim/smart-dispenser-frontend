@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.dispenser.ui.popups

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun RecipeConfirmDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,          // ✅ 예 버튼: 제조중 화면으로 이동
    onDismissAndBack: () -> Unit,   // ✅ 아니오 버튼: 팝업 닫기
    onRetry: () -> Unit             // ✅ 다시듣기: 팝업 유지 (닫히지 않음)
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissAndBack,
            shape = RoundedCornerShape(20.dp),
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "떡볶이 소스\n1인분",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    listOf(
                        "고추장 가루 3큰술",
                        "고춧가루 1큰술",
                        "간장 1큰술",
                        "설탕 1.5큰술",
                        "다시다 1/2 작은술"
                    ).forEach {
                        Text(
                            text = it,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // ✅ 예 버튼 → 제조중 화면으로 이동
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("예", fontSize = 16.sp)
                    }

                    // ✅ 아니오 버튼 → 팝업 닫기
                    Button(
                        onClick = onDismissAndBack,
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE0E0E0),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("아니오", fontSize = 16.sp)
                    }
                }
            },

            // ✅ 다시듣기 → 팝업 유지
            dismissButton = {
                Button(
                    onClick = onRetry,
                    modifier = Modifier.height(48.dp).fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE0E0E0),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("다시듣기", fontSize = 16.sp)
                }
            },
            containerColor = Color(0xFFFAFAFA)
        )
    }
}
