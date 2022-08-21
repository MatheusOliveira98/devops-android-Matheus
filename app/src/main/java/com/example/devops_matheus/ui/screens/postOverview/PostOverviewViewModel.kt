package com.example.devops_matheus.ui.screens.postOverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.devops_matheus.ui.database.posts.Post
import com.example.devops_matheus.ui.database.posts.PostDatabaseDao

class PostOverviewViewModel(val database: PostDatabaseDao, app: Application): AndroidViewModel(app) {
    val posts = database.getAllPostsLive()

    private val _navigateToSelectedPost = MutableLiveData<Post?>()
    val navigateToSelectedPost: LiveData<Post?>
        get() = _navigateToSelectedPost

    fun displayPostDetails(post: Post) {
        _navigateToSelectedPost.value = post
    }

    fun displayPostDetailsComplete() {
        _navigateToSelectedPost.value = null
    }
}