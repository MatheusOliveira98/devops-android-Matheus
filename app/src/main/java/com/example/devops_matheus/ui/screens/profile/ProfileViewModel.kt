package com.example.devops_matheus.ui.screens.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.auth0.android.result.UserProfile
import com.example.devops_matheus.ui.database.posts.Post
import timber.log.Timber

class ProfileViewModel(user: UserProfile, application: Application): AndroidViewModel(application) {

    private val _currentUser = MutableLiveData<UserProfile>()
    val currentUser: LiveData<UserProfile>
        get() = _currentUser


    init {
        _currentUser.value = user
        Timber.i("ProfileViewModel init is called")
        Timber.i(user.getId())
        Timber.i(currentUser.value?.getId())
    }
}