package com.example.junklabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.junklabs.features.AuthNavigation
import com.example.junklabs.ui.theme.JunkLabsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JunkLabsTheme {
                AuthNavigation()
            }
        }
    }
}