package com.example.chatapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.chatapp.common.rememberImeState
import com.example.chatapp.ui.composable.TopBar
import com.example.chatapp.ui.theme.AppTheme

@Composable
fun RegisterScreen(navController: NavController) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.scrollTo(scrollState.maxValue / 2)
        }
    }
    Column(
        modifier = Modifier
            .background(AppTheme.colorScheme.background)
            .fillMaxSize()
            .padding(AppTheme.size.normal)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        TopBar(icon = Icons.Filled.ArrowBackIos, iconDescription = "Back") {
            navController.popBackStack()
        }
        Text(text = "Register Screen")
    }
}

