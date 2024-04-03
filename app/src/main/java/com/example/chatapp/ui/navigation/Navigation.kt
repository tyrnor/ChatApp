package com.example.chatapp.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatapp.common.slideInFadeInFromLeft
import com.example.chatapp.common.slideInFadeInFromRight
import com.example.chatapp.common.slideOutFadeOutFromLeft
import com.example.chatapp.common.slideOutFadeOutFromRight
import com.example.chatapp.data.model.AuthenticatedUser
import com.example.chatapp.domain.model.NavigationDirection
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.view.LoginScreen
import com.example.chatapp.ui.view.RegisterScreen
import com.example.chatapp.ui.view.home.ChatScreen
import com.example.chatapp.ui.view.home.ChatsScreen
import com.example.chatapp.ui.view.home.ContactsScreen
import com.example.chatapp.ui.view.home.HomeScreen
import com.example.chatapp.ui.view.home.SearchScreen
import com.example.chatapp.ui.view.home.SettingsScreen
import com.example.chatapp.ui.viewmodel.AuthenticationViewModel
import com.example.chatapp.ui.viewmodel.HomeViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Login.route) {
        composable(Login.route, enterTransition = {
            slideInFadeInFromRight()
        }, exitTransition = {
            slideOutFadeOutFromLeft()
        }) {
            LoginScreen(
                navController = navController, authenticationViewModel = authenticationViewModel
            )
        }
        composable(Register.route, enterTransition = {
            slideInFadeInFromLeft()
        }, exitTransition = {
            slideOutFadeOutFromRight()
        }) {
            RegisterScreen(
                navController = navController, authenticationViewModel = authenticationViewModel
            )
        }
        composable(
            Home.route,
        ) {
            HomeScreen(
                authenticationViewModel = authenticationViewModel
            )
        }
    }

}

@Composable
fun HomeNavigation(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    user: AuthenticatedUser,
) {
    NavHost(
        navController = navController,
        startDestination = Chats.route,
        modifier = Modifier.background(
            AppTheme.colorScheme.background
        )
    ) {
        composable(Contacts.route, enterTransition = {
            if (homeViewModel.navigationDirection.value == NavigationDirection.RIGHT_TO_LEFT) {
                slideInFadeInFromRight()
            } else {
                fadeIn(animationSpec = tween(100))
            }
        }, exitTransition = {
            if (homeViewModel.navigationDirection.value == NavigationDirection.LEFT_TO_RIGHT) {
                slideOutFadeOutFromLeft()
            } else {
                fadeOut(animationSpec = tween(100))
            }

        }) { ContactsScreen(navController) }
        composable(Chats.route, enterTransition = {
            if (homeViewModel.navigationDirection.value == NavigationDirection.LEFT_TO_RIGHT) {
                slideInFadeInFromLeft()
            } else {
                slideInFadeInFromRight()
            }
        }, exitTransition = {
            if (homeViewModel.navigationDirection.value == NavigationDirection.LEFT_TO_RIGHT) {
                slideOutFadeOutFromLeft()
            } else {
                slideOutFadeOutFromRight()
            }
        }) { ChatsScreen(navController) }
        composable(Settings.route, enterTransition = {
            slideInFadeInFromLeft()
        }, exitTransition = {
            slideOutFadeOutFromRight()
        }) { SettingsScreen(user) }
        composable(Search.route,
            enterTransition = {
                fadeIn(animationSpec = tween(100))
            },
            exitTransition = { fadeOut(animationSpec = tween(100)) })
        {
            SearchScreen(navController, user)
            homeViewModel.resetNavigationDirection()
        }
        composable(
            Chat.route + "/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.StringType
            })
        ) {
            ChatScreen(otherUserId = it.arguments?.getString("userId") ?: "", navController = navController,  currentUser = user)
            homeViewModel.resetNavigationDirection()
        }
    }
}

@Composable
fun NavController.isCurrentRoute(route: String): Boolean {
    val navBackStackEntry by currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    return currentRoute?.startsWith(route) == true
}
