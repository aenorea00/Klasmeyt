package com.example.klasmeyt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.klasmeyt.model.CampusPost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [CampusPost::class], version = 1, exportSchema = false)
abstract class KlasmeytDatabase : RoomDatabase() {

    abstract fun postDao(): CampusPostDao

    companion object {
        @Volatile
        private var INSTANCE: KlasmeytDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): KlasmeytDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KlasmeytDatabase::class.java,
                    "klasmeyt_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Populates initial Adamson posts on first launch
        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database.postDao())
                    }
                }
            }

            suspend fun populateInitialData(dao: CampusPostDao) {
                dao.insertPost(
                    CampusPost(
                        title = "Welcome to Klasmeyt!",
                        category = "Announcements",
                        content = "This is our official Adamson University student bulletin prototype. Share your thoughts, ask questions, or post lost items!"
                    )
                )
                dao.insertPost(
                    CampusPost(
                        title = "IT Week Hackathon Teammates",
                        category = "CS/IT",
                        content = "Looking for 2 members who are interested in building Android Jetpack Compose applications for the upcoming departmental competition."
                    )
                )
            }
        }
    }
}