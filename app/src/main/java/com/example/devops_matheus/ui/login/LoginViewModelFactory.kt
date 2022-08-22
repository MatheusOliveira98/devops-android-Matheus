package com.example.devops_matheus.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.devops_matheus.ui.database.users.UserDatabaseDao

class LoginViewModelFactory(private val database: UserDatabaseDao, private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}