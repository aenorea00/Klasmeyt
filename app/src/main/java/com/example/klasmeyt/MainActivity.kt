package com.example.klasmeyt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.klasmeyt.navigation.AppNavGraph
import com.example.klasmeyt.viewmodel.FeedViewModel
import com.example.klasmeyt.viewmodel.FeedViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Access the custom Application instance as discussed in lecture
        val app = application as KlasmeytApplication

        // Initialize ViewModel using the repository from the Application instance
        val feedViewModel: FeedViewModel by viewModels {
            FeedViewModelFactory(app.repository)
        }

        setContent {
            AppNavGraph(feedViewModel = feedViewModel)
        }
    }
}