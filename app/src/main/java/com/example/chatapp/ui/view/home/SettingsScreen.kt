package com.example.chatapp.ui.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chatapp.ui.theme.AppTheme

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colorScheme.background)) {
        Text(text = "Settings")
    }
}