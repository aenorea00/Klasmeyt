package com.example.klasmeyt.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.klasmeyt.*
import com.example.klasmeyt.viewmodel.FeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: FeedViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToFeed: () -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onLogout: () -> Unit
) {
    val posts by viewModel.posts.collectAsState()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "jan.aeron.azures",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
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
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier.height(56.dp)
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToFeed,
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = TextSecondary) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToCreate,
                    icon = { Icon(Icons.Default.AddBox, contentDescription = "Post", tint = TextSecondary) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile", tint = AdUNavyPrimary) }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = AdUNavyPrimary,
                            modifier = Modifier.size(76.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "J",
                                    color = Color.White,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ProfileStatColumn(count = "${posts.size}", label = "Posts")
                            ProfileStatColumn(count = "248", label = "Followers")
                            ProfileStatColumn(count = "100%", label = "Falcon")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Jan Aeron Azures",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = AdUNavyPrimary
                    )
                    Text(
                        text = "BSIT • Adamson University 🦅",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Text(
                        text = "Vincentian Student • Web & Mobile Developer",
                        fontSize = 13.sp,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Edit profile", color = TextPrimary, fontSize = 13.sp)
                        }
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Share profile", color = TextPrimary, fontSize = 13.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(thickness = 0.5.dp, color = InstagramDivider)

                    Text(
                        text = "YOUR POSTS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AdUNavyPrimary,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }

            items(posts, key = { it.id }) { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .clickable { onNavigateToDetail(post.id) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = InstagramSurface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = post.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = AdUNavyPrimary
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AdUGoldSubtle
                            ) {
                                Text(
                                    text = post.category,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AdUNavyPrimary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = post.content,
                            fontSize = 13.sp,
                            color = TextSecondary,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileStatColumn(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {Text(text = count, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
        Text(text = label, fontSize = 12.sp, color = TextSecondary)
    }
}