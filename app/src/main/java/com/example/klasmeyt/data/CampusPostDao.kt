package com.example.klasmeyt.data

import androidx.room.*
import com.example.klasmeyt.model.CampusPost
import kotlinx.coroutines.flow.Flow

@Dao
interface CampusPostDao {

    @Query("SELECT * FROM campus_posts")
    fun getAllPosts(): Flow<List<CampusPost>>

    @Query("SELECT * FROM campus_posts WHERE id = :id LIMIT 1")
    suspend fun getPostById(id: String): CampusPost?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: CampusPost)

    @Update
    suspend fun updatePost(post: CampusPost)

    @Query("DELETE FROM campus_posts WHERE id = :id")
    suspend fun deletePostById(id: String)
}