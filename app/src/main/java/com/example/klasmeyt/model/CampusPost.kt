package com.example.klasmeyt.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "campus_posts")
data class CampusPost(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val author: String = "Jan Aeron Azures",
    val studentNumber: String = "202X-XXXXX",
    val title: String,
    val category: String,
    val content: String,
    val timestamp: String = "Just now"
)