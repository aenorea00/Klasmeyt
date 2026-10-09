package com.example.klasmeyt.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.klasmeyt.ui.screens.CreateEditPostScreen
import com.example.klasmeyt.ui.screens.FeedScreen
import com.example.klasmeyt.ui.screens.LoginScreen
import com.example.klasmeyt.ui.screens.PostDetailScreen
import com.example.klasmeyt.ui.screens.ProfileScreen
import com.example.klasmeyt.viewmodel.FeedViewModel

@Composable
fun AppNavGraph(
    feedViewModel: FeedViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Screen 0: Login
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Feed.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Screen 1: Feed
        composable(route = Screen.Feed.route) {
            FeedScreen(
                viewModel = feedViewModel,
                onNavigateToCreate = {
                    navController.navigate(Screen.CreatePost.route)
                },
                onNavigateToDetail = { postId ->
                    navController.navigate(Screen.PostDetail.createRoute(postId))
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        // Screen: Profile
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                viewModel = feedViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToFeed = {
                    navController.navigate(Screen.Feed.route) {
                        popUpTo(Screen.Feed.route) { inclusive = true }
                    }
                },
                onNavigateToCreate = {
                    navController.navigate(Screen.CreatePost.route)
                },
                onNavigateToDetail = { postId ->
                    navController.navigate(Screen.PostDetail.createRoute(postId))
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Screen 2: Post Detail
        composable(
            route = Screen.PostDetail.route,
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: ""
            PostDetailScreen(
                postId = postId,
                viewModel = feedViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { id ->
                    navController.navigate(Screen.EditPost.createRoute(id))
                }
            )
        }

        // Screen 3 (Create): Create Post
        composable(route = Screen.CreatePost.route) {
            CreateEditPostScreen(
                postId = null,
                viewModel = feedViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Screen 3 (Edit): Edit Existing Post
        composable(
            route = Screen.EditPost.route,
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            CreateEditPostScreen(
                postId = postId,
                viewModel = feedViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}