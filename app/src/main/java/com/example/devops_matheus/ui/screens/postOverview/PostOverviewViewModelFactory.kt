package com.example.devops_matheus.ui.screens.postOverview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.devops_matheus.ui.database.posts.PostDatabaseDao

class PostOverviewViewModelFactory(private val dataSource: PostDatabaseDao, private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostOverviewViewModel::class.java)) {
            return PostOverviewViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewmModel class")
    }
}