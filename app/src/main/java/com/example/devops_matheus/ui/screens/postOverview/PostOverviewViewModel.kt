package com.example.devops_matheus.ui.screens.postOverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.devops_matheus.ui.database.posts.PostDatabaseDao

class PostOverviewViewModel(val database: PostDatabaseDao, app: Application): AndroidViewModel(app) {
    val posts = database.getAllPostsLive()
}