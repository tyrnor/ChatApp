package com.example.chatapp.ui.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.ui.composables.HomeTopBar
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.viewmodel.ChatViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(userId: String, navController: NavController) {

    val chatViewModel: ChatViewModel = hiltViewModel()
    val userInformationResult by chatViewModel.userInformation.collectAsState()

    LaunchedEffect(userId) {
        chatViewModel.fetchUserById(userId)
    }


    when {
        userInformationResult.isSuccess -> {
            val user = userInformationResult.getOrDefault(UserInformation())
            if (user != null) {
                Scaffold(
                    topBar = {
                        HomeTopBar(
                            title = user.displayName,
                            leftIcon = Icons.Filled.ArrowBackIos,
                            leftIconDescription = "Back",
                            onClickLeft = { navController.popBackStack() },
                            rightIcon = Icons.Filled.Circle,
                            rightIconDescription = "Profile image"
                        ) {
                            navController.popBackStack()
                        }
                    },
                    bottomBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = AppTheme.size.small)
                                .height(48.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                        }
                    }
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        Text(text = "test")
                    }
                }
            }
        }

        userInformationResult.isFailure -> {

        }

    }

}