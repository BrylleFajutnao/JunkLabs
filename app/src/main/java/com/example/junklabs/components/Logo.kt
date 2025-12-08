package com.example.junklabs.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.junklabs.ui.theme.PrimaryBlue

@Composable
fun Logo() {
    Text(
        text = "JunkLabs",
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryBlue
    )
}