package com.example.devops_matheus.ui.screens.posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.devops_matheus.ui.database.posts.PostDatabaseDao
import com.example.devops_matheus.ui.database.posts.Post
import timber.log.Timber

class PostViewModel(val database: PostDatabaseDao, application: Application): AndroidViewModel(application) {

    private val _selectedPost = MutableLiveData<Post>()
    val selectedPost: LiveData<Post>
        get() = _selectedPost


    init {
        Timber.i("PostViewModel init is called")
    }
}