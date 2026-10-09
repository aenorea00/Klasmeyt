package com.example.klasmeyt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.klasmeyt.*
import com.example.klasmeyt.viewmodel.FeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditPostScreen(
    postId: String?,
    viewModel: FeedViewModel,
    onNavigateBack: () -> Unit
) {
    val isEditMode = postId != null
    val posts by viewModel.posts.collectAsState()
    val existingPost = posts.find { it.id == postId }

    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isInitialized by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    LaunchedEffect(existingPost) {
        if (isEditMode && existingPost != null && !isInitialized) {
            title = existingPost.title
            category = existingPost.category
            content = existingPost.content
            isInitialized = true
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEditMode) "Edit Post" else "Create Post",
                        fontWeight = FontWeight.Bold,
                        color = AdUNavyPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AdUNavyPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    showError = false
                },
                label = { Text("Post Title") },
                placeholder = { Text("e.g., MobDev Study Group") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                isError = showError && title.isBlank()
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category Tag") },
                placeholder = { Text("e.g., CS/IT, Announcements, Lost & Found") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            OutlinedTextField(
                value = content,
                onValueChange = {
                    content = it
                    showError = false
                },
                label = { Text("Content") },
                placeholder = { Text("Write your message for fellow Adamsonians...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                maxLines = 10,
                shape = RoundedCornerShape(10.dp),
                isError = showError && content.isBlank()
            )

            if (showError) {
                Text(
                    text = "Title and Content cannot be empty.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    if (title.isBlank() || content.isBlank()) {
                        showError = true
                    } else {
                        if (isEditMode && postId != null) {
                            viewModel.updatePost(
                                id = postId,
                                newTitle = title,
                                newCategory = category,
                                newContent = content
                            )
                        } else {
                            viewModel.addPost(
                                title = title,
                                category = category,
                                content = content
                            )
                        }
                        onNavigateBack()
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AdUNavyPrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    if (isEditMode) "Save Changes" else "Post to Feed",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
        }
    }
}