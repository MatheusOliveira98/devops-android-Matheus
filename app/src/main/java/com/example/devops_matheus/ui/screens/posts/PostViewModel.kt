package com.example.devops_matheus.ui.screens.posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.devops_matheus.ui.database.posts.PostDatabaseDao
import com.example.devops_matheus.ui.database.posts.Post
import kotlinx.coroutines.launch
import timber.log.Timber

class PostViewModel(val database: PostDatabaseDao, application: Application): AndroidViewModel(application) {

    private var _selectedPost = MutableLiveData<Post>()
    val selectedPost: LiveData<Post>
        get() = _selectedPost


    init {
        Timber.i("PostViewModel init is called")
    }

    fun setPost(postId: Long) {
        viewModelScope.launch {
            _selectedPost.value = database.get(postId)
        }
    }
}