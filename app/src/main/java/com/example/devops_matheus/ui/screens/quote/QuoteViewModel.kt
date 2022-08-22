package com.example.devops_matheus.ui.screens.quote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devops_matheus.ui.database.quote.QuoteDatabase
import com.example.devops_matheus.ui.repository.QuoteRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class QuoteViewModel(val database: QuoteDatabase, application: Application) : AndroidViewModel(application) {
    private val quoteRepository = QuoteRepository(database)

    val currentQuote = quoteRepository.quote

    init {
        viewModelScope.launch {
            quoteRepository.refreshQuote()
        }
    }
}