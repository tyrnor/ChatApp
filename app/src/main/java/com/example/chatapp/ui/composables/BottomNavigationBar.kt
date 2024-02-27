package com.example.chatapp.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapp.domain.model.BottomNavigationItem
import com.example.chatapp.domain.model.Screen
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.theme.DarkGrey
import com.example.chatapp.ui.theme.LightGrey
import com.example.chatapp.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    navController: NavController,
    homeViewModel: HomeViewModel,
) {

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Column {
        Divider(thickness = 1.dp, color = LightGrey, modifier = Modifier.padding(horizontal = AppTheme.size.small))
        NavigationBar(containerColor = AppTheme.colorScheme.background) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    label = {
                        Text(
                            text = item.title,
                            style = AppTheme.typography.labelLarge,
                        )
                    },
                    onClick = {
                        selectedItemIndex = index
                        homeViewModel.navigateTo(Screen.fromRoute(item.title.lowercase())!!)
                        navController.navigate("home/" + item.title.lowercase()) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount != null) {
                                    Badge {
                                        Text(text = item.run { badgeCount.toString() })
                                    }
                                } else if (item.hasNews) {
                                    Badge()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title,
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = DarkGrey,
                        unselectedTextColor = DarkGrey,
                        selectedIconColor = AppTheme.colorScheme.primary,
                        selectedTextColor = AppTheme.colorScheme.primary,
                        indicatorColor = AppTheme.colorScheme.background
                    )
                )
            }
        }
    }

}