package com.example.devops_matheus.ui.screens.quote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.devops_matheus.ui.database.quote.QuoteDatabase
import com.example.devops_matheus.ui.database.quote.QuoteDatabaseDao
import com.example.devops_matheus.ui.database.users.UserDatabaseDao
import com.example.devops_matheus.ui.screens.profileEdit.ProfileEditViewModel

class QuoteViewModelFactory(private val database: QuoteDatabase, private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuoteViewModel::class.java)) {
            return QuoteViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}