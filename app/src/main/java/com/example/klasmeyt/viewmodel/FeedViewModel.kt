package com.example.klasmeyt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klasmeyt.data.PostRepository
import com.example.klasmeyt.model.CampusPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FeedViewModel(private val repository: PostRepository) : ViewModel() {

    val posts: StateFlow<List<CampusPost>> = repository.allPosts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    fun getPostById(id: String): CampusPost? {
        return posts.value.find { it.id == id }
    }

    fun addPost(title: String, category: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newPost = CampusPost(
                title = title.trim(),
                category = category.trim().ifEmpty { "General" },
                content = content.trim()
            )
            repository.insert(newPost)
        }
    }

    fun updatePost(id: String, newTitle: String, newCategory: String, newContent: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val existing = getPostById(id) ?: return@launch
            val updated = existing.copy(
                title = newTitle.trim(),
                category = newCategory.trim().ifEmpty { "General" },
                content = newContent.trim()
            )
            repository.update(updated)
        }
    }

    fun deletePost(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(id)
        }
    }
}