package com.example.klasmeyt

import android.app.Application
import com.example.klasmeyt.data.KlasmeytDatabase
import com.example.klasmeyt.data.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class KlasmeytApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { KlasmeytDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PostRepository(database.postDao()) }
}