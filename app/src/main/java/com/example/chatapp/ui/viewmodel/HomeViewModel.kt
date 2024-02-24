package com.example.chatapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.chatapp.domain.model.NavigationDirection
import com.example.chatapp.domain.model.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _navigationDirection = MutableStateFlow<NavigationDirection?>(null)
    val navigationDirection: StateFlow<NavigationDirection?> = _navigationDirection

    private val _lastScreen = MutableStateFlow(Screen.Contacts)
    val lastScreen: StateFlow<Screen> = _lastScreen

    fun navigateTo(targetScreen: Screen) {
        val direction = getNavigationDirection(_lastScreen.value, targetScreen)
        _navigationDirection.value = direction
        _lastScreen.value = targetScreen
    }

    private fun getNavigationDirection(currentScreen: Screen, targetScreen: Screen): NavigationDirection {
        return when {
            currentScreen.ordinal < targetScreen.ordinal -> NavigationDirection.LEFT_TO_RIGHT
            else -> NavigationDirection.RIGHT_TO_LEFT
        }
    }

    fun resetNavigationDirection() {
        _navigationDirection.value = null
    }
}