package com.example.devops_matheus.ui.screens.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.devops_matheus.ui.database.users.UserDatabaseDao

class ProfileViewModelFactory(private val database: UserDatabaseDao, private val userId: String, private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(database, userId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}