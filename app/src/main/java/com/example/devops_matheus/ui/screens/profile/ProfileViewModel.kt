package com.example.devops_matheus.ui.screens.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.auth0.android.result.UserProfile
import com.example.devops_matheus.ui.database.posts.Post
import com.example.devops_matheus.ui.database.users.User
import com.example.devops_matheus.ui.database.users.UserDatabaseDao
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(val database: UserDatabaseDao, val userId: String, application: Application): AndroidViewModel(application) {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser


    private val _editEvent = MutableLiveData<Boolean>()
    val editEvent: LiveData<Boolean>
        get() = _editEvent

    fun editPostClick() {
        _editEvent.value = true
    }

    fun editEventDone() {
        _editEvent.value = false
    }

    init {
        Timber.i("ProfileViewModel init is called")
        setUser()
        Timber.i(userId)
        Timber.i(currentUser.value?.userId)
        _editEvent.value = false
    }

    fun setUser() {
        viewModelScope.launch {
            _currentUser.value = database.get(userId)
        }
    }
}