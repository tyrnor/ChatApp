package com.example.chatapp.ui.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatapp.ui.viewmodel.ContactsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(){
    val contactsViewModel: ContactsViewModel = hiltViewModel()

     var searchQuery by rememberSaveable {
         mutableStateOf("")
     }

    Column {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search by name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear")
                    }
                }
            }
        )
        UserList(query = searchQuery, viewModel = contactsViewModel)
    }

}

@Composable
fun UserList(query: String, viewModel: ContactsViewModel) {
    LaunchedEffect(query) {
        viewModel.searchUsers(query)
    }

    val searchResults by viewModel.searchResults.collectAsState()

    when {
        searchResults.isSuccess -> {
            val users = searchResults.getOrNull() ?: emptyList()
            LazyColumn (modifier = Modifier.fillMaxSize()) {
                items(items = users) {user ->
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