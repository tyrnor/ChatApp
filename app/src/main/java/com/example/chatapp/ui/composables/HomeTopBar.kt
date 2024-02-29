package com.example.chatapp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.chatapp.ui.theme.AppTheme

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    title: String,
    leftIcon: ImageVector?,
    leftIconDescription: String?,
    rightIcon : ImageVector?,
    rightIconDescription: String?,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.size.small)
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (leftIcon != null) {
            Icon(
                imageVector = leftIcon,
                contentDescription = leftIconDescription,
                modifier = Modifier
                    .clickable { onClickLeft() }
                    .size(48.dp)
                    .padding(AppTheme.size.small),
                tint = AppTheme.colorScheme.icons
            )
        }
        Text(text = title, modifier = Modifier.align(Alignment.CenterVertically))
        if (rightIcon != null) {
            Icon(
                imageVector = rightIcon,
                contentDescription = rightIconDescription,
                modifier = Modifier
                    .clickable { onClickRight() }
                    .size(48.dp)
                    .padding(AppTheme.size.small),
                tint = AppTheme.colorScheme.icons
            )
        }
    }

}