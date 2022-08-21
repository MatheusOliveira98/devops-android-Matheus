package com.example.devops_matheus.ui.screens.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.result.UserProfile

class ProfileViewModelFactory(private val user: UserProfile, private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(user, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}