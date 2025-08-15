package com.example.dispenser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// 상태 색상 (흰 글자 대비 충분)
private val StatusBlue = Color(0xFF1976D2) // 완료
private val StatusRed  = Color(0xFFD32F2F) // 실패
private val StatusGray = Color(0xFF757575) // 중단

/**
 * 서버 status: REQUESTED, PROCESSING, SUCCESS, FAIL
 * - SUCCESS -> "완료"(파랑)
 * - FAIL    -> "실패"(빨강)
 * - REQUESTED/PROCESSING -> "중단"(회색)
 * - 그 외/ null -> 표시 안 함
 */
@Composable
fun StatusBadge(
    status: String?,
    modifier: Modifier = Modifier
) {
    // Pair 없이 로컬 변수에 직접 할당 → IDE 밑줄/타입 추론 문제 회피
    val label: String
    val bg: Color
    when (status) {
        "SUCCESS" -> { label = "완료"; bg = StatusBlue }
        "FAIL" -> { label = "실패"; bg = StatusRed }
        "REQUESTED", "PROCESSING" -> { label = "중단"; bg = StatusGray }
        else -> return
    }

    Box(
        modifier = modifier
            .background(bg, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
