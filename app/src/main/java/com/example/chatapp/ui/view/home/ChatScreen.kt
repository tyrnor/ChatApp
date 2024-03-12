package com.example.chatapp.ui.view.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.common.keyboardAsState
import com.example.chatapp.data.model.AuthenticatedUser
import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.ui.composables.HomeTopBar
import com.example.chatapp.ui.composables.MessageContainer
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.theme.DarkGrey
import com.example.chatapp.ui.theme.Grey
import com.example.chatapp.ui.theme.Purple2
import com.example.chatapp.ui.viewmodel.ChatViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(otherUserId: String, navController: NavController, currentUser: AuthenticatedUser?) {

    val chatViewModel: ChatViewModel = hiltViewModel()
    val userInformationResult by chatViewModel.userInformation.collectAsState()
    val chatId by chatViewModel.chatId.collectAsState()
    val messages by chatViewModel.messages.collectAsState()

    LaunchedEffect(otherUserId) {
        chatViewModel.fetchUserById(otherUserId)
        chatViewModel.getChatId(currentUser!!.userId, otherUserId)
    }

    LaunchedEffect(chatId) {
        chatId?.let {
            chatViewModel.listenForMessages(it)
        }
    }

    var message by rememberSaveable {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    if (!isKeyboardOpen) focusManager.clearFocus()


    when {
        userInformationResult.isSuccess -> {
            val user = userInformationResult.getOrDefault(UserInformation())
            if (user != null && chatId != null) {
                Log.d("TAG", "ChatScreen: $chatId")
                Scaffold(
                    modifier = Modifier.background(AppTheme.colorScheme.background),
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
                                .padding(AppTheme.size.small),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(AppTheme.shape.container)
                                    .weight(0.85f)
                                    .background(Grey)
                            ) {
                                BasicTextField(
                                    value = message,
                                    onValueChange = { message = it },
                                    singleLine = false,
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    maxLines = 5,
                                    decorationBox = { innerTextField ->
                                        Box(
                                            contentAlignment = Alignment.CenterStart,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(AppTheme.size.small)
                                        ) {
                                            if (message.isEmpty()) {
                                                Text(
                                                    "Message",
                                                    style = AppTheme.typography.labelLarge,
                                                    color = DarkGrey
                                                )
                                            }
                                            innerTextField()
                                        }
                                    },
                                    textStyle = AppTheme.typography.labelLarge.copy(color = AppTheme.colorScheme.onBackground),
                                    cursorBrush = SolidColor(AppTheme.colorScheme.onBackground),
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                                )

                            }
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "Send message",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = AppTheme.size.small)
                                    .size(24.dp)
                                    .clickable { }
                            )

                        }
                    }
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .background(AppTheme.colorScheme.chatBackground)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(AppTheme.size.small)
                        ) {
                            items(messages) { message ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = AppTheme.size.small),
                                    horizontalArrangement = if (message.senderId == currentUser?.userId) Arrangement.End else Arrangement.Start
                                ) {
                                    MessageContainer(
                                        modifier = if (message.senderId == currentUser?.userId) Modifier.padding(
                                            start = AppTheme.size.large
                                        ) else Modifier.padding(end = AppTheme.size.large),
                                        alignment = if (message.senderId == currentUser?.userId) Alignment.End else Alignment.Start,
                                        color = if (message.senderId == currentUser?.userId) Purple2 else AppTheme.colorScheme.chatContainer,
                                        text = message.text
                                    )

                                }
                                Spacer(modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        }

        userInformationResult.isFailure -> {

        }

    }

}