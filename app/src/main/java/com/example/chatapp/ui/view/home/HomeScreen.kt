package com.example.chatapp.ui.view.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.domain.model.BottomNavigationItem
import com.example.chatapp.ui.composables.BottomNavigationBar
import com.example.chatapp.ui.navigation.HomeNavigation
import com.example.chatapp.ui.navigation.Search
import com.example.chatapp.ui.navigation.isCurrentRoute
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.viewmodel.AuthenticationViewModel
import com.example.chatapp.ui.viewmodel.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(authenticationViewModel: AuthenticationViewModel) {

    val user by authenticationViewModel.user.collectAsState()

    val homeNavController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()

    val items = listOf(
        BottomNavigationItem(
            title = "Contacts",
            selectedIcon = Icons.Filled.Contacts,
            unselectedIcon = Icons.Outlined.Contacts,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Chats",
            selectedIcon = Icons.Filled.Chat,
            unselectedIcon = Icons.Outlined.Chat,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            hasNews = false
        ),
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background),
        bottomBar = {
            if (!homeNavController.isCurrentRoute(Search.route)) {
                BottomNavigationBar(
                    items = items,
                    navController = homeNavController,
                    homeViewModel = homeViewModel
                )
            }
        }
    ) {
        HomeNavigation(homeNavController, homeViewModel, user)
    }
}