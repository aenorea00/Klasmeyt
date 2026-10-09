package com.example.klasmeyt.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Feed : Screen("feed_screen")
    object Profile : Screen("profile_screen")
    object CreatePost : Screen("create_post_screen")
    object PostDetail : Screen("post_detail_screen/{postId}") {
        fun createRoute(postId: String) = "post_detail_screen/$postId"
    }
    object EditPost : Screen("edit_post_screen/{postId}") {
        fun createRoute(postId: String) = "edit_post_screen/$postId"
    }
}