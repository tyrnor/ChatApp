package com.example.chatapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.theme.HardDarkGrey


@Composable
fun MessageContainer(modifier: Modifier, color: Color) {
    Box(
        modifier = modifier
            .clip(AppTheme.shape.container)
            .fillMaxWidth(0.9f)
            .background(color)
    ) {
        Column {

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ex est, elementum ac consectetur sed, ultricies vel tortor. Nulla aliquet nibh nec mi ullamcorper venenatis. Sed commodo purus nec consequat ullamcorper. Pellentesque quis euismod nisi. Donec scelerisque arcu vitae pharetra vestibulum. Sed lobortis magna sed commodo dapibus.",
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