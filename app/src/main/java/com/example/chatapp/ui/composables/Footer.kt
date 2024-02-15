package com.example.chatapp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.chatapp.ui.navigation.Register
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.theme.Grey

@Composable
fun Footer(text1: String, text2: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.size.medium)
    ) {

        Spacer(modifier = Modifier.size(AppTheme.size.large))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = text1, style = AppTheme.typography.labelNormal, color = Grey)
            Text(
                text = text2,
                modifier = Modifier
                    .padding(horizontal = AppTheme.size.small)
                    .clickable {
                        onClick()
                    },
                style = AppTheme.typography.labelNormal.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.size(AppTheme.size.large))
    }
}