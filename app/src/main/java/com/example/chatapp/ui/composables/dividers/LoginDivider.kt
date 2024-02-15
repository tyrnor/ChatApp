package com.example.chatapp.ui.composables.dividers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.theme.Grey
import com.example.chatapp.ui.theme.LightGrey

@Composable
fun LoginDivider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.size.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .background(LightGrey)
                .height(1.dp)
                .weight(1f)

        )
        Text(
            text = "OR",
            modifier = Modifier.padding(horizontal = AppTheme.size.medium),
            style = AppTheme.typography.labelNormal.copy(fontWeight = FontWeight.Bold),
            color = Grey
        )
        Divider(
            modifier = Modifier
                .background(LightGrey)
                .height(1.dp)
                .weight(1f)

        )
    }
}