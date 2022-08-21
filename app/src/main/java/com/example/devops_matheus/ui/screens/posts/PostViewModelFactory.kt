package com.example.devops_matheus.ui.screens.posts

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.devops_matheus.ui.database.posts.Post
import com.example.devops_matheus.ui.database.posts.PostDatabaseDao

class PostViewModelFactory(private val dataSource: PostDatabaseDao, private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}