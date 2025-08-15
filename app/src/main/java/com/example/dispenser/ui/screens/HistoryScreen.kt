@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.dispenser.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dispenser.data.model.HistoryDto
import com.example.dispenser.ui.components.StatusBadge
import com.example.dispenser.ui.popups.RecipeConfirmDialog
import com.example.dispenser.viewmodel.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.dispenser.navigation.Screen


/* ---------- API 24 호환용 날짜 포맷터 ---------- */
/** ISO-8601(마이크로초 포함 가능) 문자열을 yyyy-MM-dd HH:mm 형태로 변환 */
private fun formatDateTime(isoString: String?): String {
    if (isoString.isNullOrBlank()) return "-"
    return try {
        val normalized = normalizeIsoToMillis(isoString)
        val inFmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        val date = inFmt.parse(normalized) ?: return isoString
        val outFmt = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        outFmt.format(date)
    } catch (_: Exception) {
        isoString // 실패 시 원본 노출
    }
}

/** 2025-08-15T09:34:44.272807(+offset 가능) -> 2025-08-15T09:34:44.272 로 보정 */
private fun normalizeIsoToMillis(src: String): String {
    val datePart = src.substringBefore('T', src)
    val afterT = src.substringAfter('T', "")
    // 시간부에서 숫자/콜론/점만 취득하여 타임존/오프셋(Z, +09:00 등) 제거
    val timeCore = afterT.takeWhile { it.isDigit() || it == ':' || it == '.' }
    val secPart = timeCore.substringBefore('.', timeCore) // HH:mm:ss
    val frac = timeCore.substringAfter('.', "")            // 소수부(없으면 "")
    val millis = when {
        frac.isEmpty() -> "000"
        frac.length >= 3 -> frac.substring(0, 3)
        else -> (frac + "000").substring(0, 3)
    }
    return "${datePart}T${secPart}.${millis}"
}
/* -------------------------------------------- */

@Composable
fun HistoryScreen(
    navController: NavController,
    onBack: () -> Unit,
    onHome: () -> Unit,
    viewModel: HistoryViewModel = viewModel()
) {
    var showRecipeDialog by remember { mutableStateOf(false) }
    val ui by viewModel.ui.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadAll() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                ui.loading && ui.items.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                !ui.loading && ui.items.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(ui.error ?: "기록이 없어요.")
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(ui.items) { item: HistoryDto ->
                            val ingredientsText =
                                item.ingredientsNames.joinToString(" · ") { it.name }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { showRecipeDialog = true },
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    val iconImage =
                                        if (item.favorite) Icons.Filled.Star else Icons.Filled.StarBorder
                                    Icon(
                                        imageVector = iconImage,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .padding(top = 2.dp)
                                            .height(32.dp)
                                    )

                                    Spacer(Modifier.padding(8.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = item.recipeName.ifBlank { "레시피 #${item.recipeId}" },
                                                style = MaterialTheme.typography.headlineSmall,
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier.weight(1f)
                                            )
                                            StatusBadge(status = item.status)
                                        }

                                        Spacer(Modifier.height(6.dp))

                                        // 상태 텍스트는 제거하고 "요청"과 "재료"만 노출
                                        Text(
                                            text = "요청: ${formatDateTime(item.requestedAt)}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = Color.Gray,
                                            textAlign = TextAlign.Start
                                        )

                                        if (ingredientsText.isNotBlank()) {
                                            Spacer(Modifier.height(6.dp))
                                            Text(
                                                text = "재료: $ingredientsText",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        if (!ui.endReached) {
                            item {
                                LaunchedEffect(ui.page) {
                                    if (!ui.loading) viewModel.loadAll(next = true)
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (ui.loading) {
                                        LinearProgressIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth(0.5f)
                                                .height(6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            ui.error?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        RecipeConfirmDialog(
            showDialog = showRecipeDialog,
            onConfirm = {
                showRecipeDialog = false
                navController.navigate(Screen.Manufacturing.route) {
                    popUpTo(Screen.MemberHome.route) { inclusive = false }
                    launchSingleTop = true
                }
            },
            onDismissAndBack = { showRecipeDialog = false },
            onRetry = { /* 필요 시 재시도 로직 */ }
        )
    }
}
