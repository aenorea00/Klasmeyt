package com.example.klasmeyt.data

import com.example.klasmeyt.model.CampusPost
import kotlinx.coroutines.flow.Flow

class PostRepository(private val postDao: CampusPostDao) {

    val allPosts: Flow<List<CampusPost>> = postDao.getAllPosts()

    suspend fun getPostById(id: String): CampusPost? {
        return postDao.getPostById(id)
    }

    suspend fun insert(post: CampusPost) {
        postDao.insertPost(post)
    }

    suspend fun update(post: CampusPost) {
        postDao.updatePost(post)
    }

    suspend fun delete(id: String) {
        postDao.deletePostById(id)
    }
}