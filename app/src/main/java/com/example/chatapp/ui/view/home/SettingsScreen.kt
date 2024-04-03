package com.example.chatapp.ui.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import com.example.chatapp.data.model.AuthenticatedUser
import com.example.chatapp.ui.theme.AppTheme

@Composable
fun SettingsScreen(user: AuthenticatedUser) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
    ) {
        Icon(
            imageVector = Icons.Filled.Circle,
            contentDescription = "Image profile",
            modifier = Modifier
                .size(128.dp)
                .align(Alignment.CenterHorizontally)
                .padding(AppTheme.size.small),
            tint = AppTheme.colorScheme.icons
        )
        Text(
            text = user.displayName?.replaceFirstChar {
                it.uppercase()
            } ?: "Username not available",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}