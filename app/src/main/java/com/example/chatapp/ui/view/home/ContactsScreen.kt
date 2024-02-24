package com.example.chatapp.ui.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatapp.ui.navigation.Search
import com.example.chatapp.ui.theme.AppTheme

@Composable
fun ContactsScreen(navController: NavHostController) {

    Column (modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { navController.navigate(Search.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.size.medium)
                .height(40.dp)
            ,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colorScheme.primary,
                contentColor = AppTheme.colorScheme.onPrimary,
            ),
            shape = AppTheme.shape.button
        ) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "search", tint = AppTheme.colorScheme.onPrimary)
            Text(text = "Search", style = AppTheme.typography.labelNormal)
        }
    }
}

