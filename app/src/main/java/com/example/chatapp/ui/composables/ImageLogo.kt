package com.example.chatapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.chatapp.R
import com.example.chatapp.ui.theme.AppTheme

@Composable
fun ImageLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = if (!isSystemInDarkTheme()) R.drawable.logo else R.drawable.logo_darkmode),
        contentDescription = "Logo",
        modifier = modifier.background(AppTheme.colorScheme.background)
    )
}