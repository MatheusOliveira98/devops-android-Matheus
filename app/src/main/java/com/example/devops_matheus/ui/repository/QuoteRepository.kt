package com.example.devops_matheus.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.devops_matheus.ui.database.quote.Quote
import com.example.devops_matheus.ui.database.quote.QuoteDatabase
import com.example.devops_matheus.ui.network.QuoteApi
import kotlinx.coroutines.*

class QuoteRepository(private val database: QuoteDatabase) {

    val quote: LiveData<Quote> = database.quoteDatabaseDao.getLastQuote()


    suspend fun refreshQuote() {
        withContext(Dispatchers.IO) {
            val quoteFromNetwork = QuoteApi.retrofitService.getQuoteAsync().await()
            database.quoteDatabaseDao.insert(quoteFromNetwork)
        }
    }
}