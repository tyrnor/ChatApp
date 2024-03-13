package com.example.chatapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.theme.HardDarkGrey


@Composable
fun MessageContainer(
    modifier: Modifier = Modifier,
    color: Color,
    text: String) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .clip(AppTheme.shape.container)
            .defaultMinSize(minWidth = 75.dp)
            .background(color)
    ) {
        Column(Modifier.defaultMinSize(minWidth = 75.dp)) {
            Text(
                text = text,
                color = AppTheme.colorScheme.chatText,
                style = AppTheme.typography.labelNormal,
                modifier = Modifier.padding(
                    start = AppTheme.size.small,
                    end = AppTheme.size.small,
                    top = AppTheme.size.small
                )
            )

            Text(
                text = "19:12",
                color = HardDarkGrey.copy(alpha = 0.6f),
                style = AppTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = AppTheme.size.small, bottom = AppTheme.size.small)
            )
        }
    }
}