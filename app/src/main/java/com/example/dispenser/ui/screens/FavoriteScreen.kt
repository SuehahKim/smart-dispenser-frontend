@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.dispenser.ui.screens


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.dispenser.ui.popups.StockAlertDialog
import com.example.dispenser.ui.popups.RecipeConfirmDialog




data class FavoriteItem(val id: Int, val name: String, val description: String)

@Composable
fun FavoriteScreen(
    onBack: () -> Unit,
    onHome: () -> Unit
) {
    // 더미 데이터
    val favorites = remember {
        listOf(
            FavoriteItem(1,  "떡볶이소스",      "고추장 • 고춧가루 • 설탕 • 간장 • 물엿"),
            FavoriteItem(2,  "비빔국수소스",   "고추장 • 식초 • 설탕 • 참기름 • 다진 마늘"),
            FavoriteItem(3,  "제육볶음소스",   "고추장 • 간장 • 설탕 • 다진 마늘 • 후추"),
            FavoriteItem(4,  "스위트칠리소스", "칠리소스 베이스 • 식초 • 설탕 • 다진 마늘"),
            FavoriteItem(5,  "스파이시간장",    "간장 • 고춧가루 • 다진 마늘 • 참기름"),
            FavoriteItem(6,  "갈릭버터소스",   "버터 • 다진 마늘 • 파슬리"),
            FavoriteItem(7,  "머스터드소스",   "머스터드 • 꿀 • 마요네즈"),
            FavoriteItem(8,  "발사믹비네그레트", "발사믹 식초 • 올리브 오일 • 소금 • 설탕"),
            FavoriteItem(9,  "토마토베이스",   "토마토 페이스트 • 올리브 오일 • 허브"),
            FavoriteItem(10, "BBQ소스",        "토마토 소스 • 설탕 • 식초 • 스모크 향")
        )
    }

    // 팝업 상태
    var showRecipeDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 빈 블록 */ },
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favorites) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                if (item.name == "떡볶이소스") {
                                    showRecipeDialog = true
                                }
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = item.description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }
                }
            }

            // 팝업 호출
            RecipeConfirmDialog(
                showDialog = showRecipeDialog,
                onConfirm = {
                    showRecipeDialog = false
                    // 예 버튼 로직
                },
                onDismissAndBack = {
                    showRecipeDialog = false
                },
                onRetry = {
                    // 다시듣기 로직 (팝업을 유지하고 필요한 작업만 수행)
                    // 예: 텍스트 음성으로 다시 읽기
                }
            )
        }
    )
}
