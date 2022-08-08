package com.example.devops_matheus.ui.screens.postCreation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.devops_matheus.ui.database.posts.Post
import com.example.devops_matheus.ui.database.posts.PostDatabaseDao
import kotlinx.coroutines.launch

class PostCreationViewModel(val database: PostDatabaseDao, application: Application): AndroidViewModel(application) {

    private val _saveEvent = MutableLiveData<Boolean>()
    val saveEvent: LiveData<Boolean>
        get() = _saveEvent

    init {
        _saveEvent.value = false
    }

    fun savePostClick() {
        _saveEvent.value = true
    }

    fun saveEventDone() {
        _saveEvent.value = false
    }

    fun savePost(newPostText: String, newPostLink: String) {
        viewModelScope.launch {
            val post = Post()
            post.postText = newPostText
            post.postLink = newPostLink
            savePostToDatabase(post)
        }
    }

    suspend fun savePostToDatabase(newPost: Post) {
        database.insert(newPost)
    }
}