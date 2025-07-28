@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.dispenser.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun QRScanScreen(
    onScanSuccess: (String) -> Unit,
    onBack: () -> Unit
) {
    // 1) ScanContract 런처 등록
    val qrLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        result.contents?.let { qr ->
            onScanSuccess(qr)
        } ?: run {
            onBack()
        }
    }

    // 2) 진입하자마자 스캔 시작
    LaunchedEffect(Unit) {
        qrLauncher.launch(
            ScanOptions().apply {
                setPrompt("양념 디스펜서를 스캔하세요")
                setBeepEnabled(true)
                setOrientationLocked(true)
            }
        )
    }

    // 3) 스캔 중 안내 UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("스캔 준비 중… QR 코드를 비춰주세요")
    }
}
