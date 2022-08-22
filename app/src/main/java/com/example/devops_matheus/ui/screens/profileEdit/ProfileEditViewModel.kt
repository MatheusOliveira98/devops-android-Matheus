package com.example.devops_matheus.ui.screens.profileEdit

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.*
import com.example.devops_matheus.ui.database.posts.Post
import com.example.devops_matheus.ui.database.users.User
import com.example.devops_matheus.ui.database.users.UserDatabaseDao
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileEditViewModel(val database: UserDatabaseDao, val userId: String, application: Application): AndroidViewModel(application) {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser


    private val _saveEvent = MutableLiveData<Boolean>()
    val saveEvent: LiveData<Boolean>
        get() = _saveEvent

    fun saveProfileClick() {
        _saveEvent.value = true
    }

    fun saveEventDone() {
        _saveEvent.value = false
    }

    init {
        Timber.i("ProfileViewModel init is called")
        setUser()
        _saveEvent.value = false
    }


    fun setUser() {
        viewModelScope.launch {
            _currentUser.value = database.get(userId)
        }
    }

    fun saveUser(newAvatar: Bitmap?, newUserName: String) {
        viewModelScope.launch {
            val user = currentUser.value!!
            user.userAvatar = newAvatar
            user.userName = newUserName
            saveUserToDatabase(user)
            _currentUser.value = database.get(user.userId)
        }
    }

    suspend fun saveUserToDatabase(user: User) {
        database.update(user)
    }
}