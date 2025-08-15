package com.example.dispenser.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dispenser.navigation.Screen


@Composable
fun StartManufacturingButton(navController: NavController) {
    Button(
        onClick = { navController.navigate(Screen.Manufacturing.route) },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xB0BDD5E0)) // 연한 블루그레이
    ) {
        Text("제조 시작")
    }
}
