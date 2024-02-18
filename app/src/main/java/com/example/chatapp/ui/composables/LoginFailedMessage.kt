package com.example.chatapp.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chatapp.ui.theme.AppTheme

@Composable
fun LoginFailedMessage(message: String) {
    Row (modifier = Modifier.padding(horizontal = AppTheme.size.small, vertical = AppTheme.size.medium), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Filled.Error, contentDescription = "error icon", tint = AppTheme.colorScheme.error)
        Text(
            text = message,
            style = AppTheme.typography.labelNormal,
            modifier = Modifier.padding(horizontal = AppTheme.size.small)
        )
    }

}