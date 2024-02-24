package com.example.chatapp.ui.view.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.common.keyboardAsState
import com.example.chatapp.ui.navigation.Contacts
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.viewmodel.SearchViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navController: NavController) {
    val searchViewModel: SearchViewModel = hiltViewModel()

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {

        focusRequester.requestFocus()
    }
    val isKeyboardOpen by keyboardAsState()
    if (!isKeyboardOpen) focusManager.clearFocus()

    Column(modifier = Modifier
        .padding(AppTheme.size.medium)
        .fillMaxSize()) {
        Row (modifier = Modifier
            .height(41.dp)
            .fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .clip(AppTheme.shape.button)
                    .background(AppTheme.colorScheme.primary)
                    .weight(0.85f)
                    ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search",
                    tint = AppTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = AppTheme.size.normal)
                )
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                    ,
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxHeight()
                        ) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    "Search",
                                    style = AppTheme.typography.labelNormal,
                                    color = AppTheme.colorScheme.onPrimary
                                )
                            }
                            innerTextField()
                        }
                    },
                    textStyle = AppTheme.typography.labelNormal.copy(color = AppTheme.colorScheme.onPrimary),
                    cursorBrush = SolidColor(AppTheme.colorScheme.onPrimary),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
                )
            }
            Text(text = "Cancel", modifier = Modifier.clickable { navController.navigate(Contacts.route) })
        }
        

        UserList(query = searchQuery, viewModel = searchViewModel)
    }
}

@Composable
fun UserList(query: String, viewModel: SearchViewModel) {
    LaunchedEffect(query) {
        viewModel.searchUsers(query)
    }

    val searchResults by viewModel.searchResults.collectAsState()

    when {
        searchResults.isSuccess -> {
            val users = searchResults.getOrNull() ?: emptyList()
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = users) { user ->
                    Text(text = user.displayName)
                }
            }

        }

        searchResults.isFailure -> {
            Text("Failed to load users: ${searchResults.exceptionOrNull()?.message}")
        }

        else -> {
            Text("Loading users...")
        }
    }
}