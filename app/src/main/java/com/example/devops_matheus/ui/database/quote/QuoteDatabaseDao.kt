package com.example.devops_matheus.ui.database.quote

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuoteDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quote: Quote)

    @Query("SELECT * FROM quote_table ORDER BY quoteId DESC")
    fun getLastQuote(): LiveData<Quote>
}