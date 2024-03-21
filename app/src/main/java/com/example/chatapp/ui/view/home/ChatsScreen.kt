package com.example.chatapp.ui.view.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.ui.navigation.Chat
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.viewmodel.ChatsViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ChatsScreen(navController: NavController) {
    val chatsViewModel: ChatsViewModel = hiltViewModel()
    val chats by chatsViewModel.chats.collectAsState()
    val currentUserId = Firebase.auth.currentUser!!.uid

    LaunchedEffect(chats) {
        chatsViewModel.getUserChats(currentUserId)
    }
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colorScheme.background)) 
    {
        items(chats){
            Text(text = it.otherUserDisplayName, modifier = Modifier.clickable {
                navController.navigate(Chat.route + "/${it.otherUserId}")
            })
        }
    }
}