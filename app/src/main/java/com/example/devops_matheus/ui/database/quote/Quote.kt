package com.example.devops_matheus.ui.database.quote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote_table")
data class Quote(

    @PrimaryKey(autoGenerate = true)
    var quoteId: Long = 0L,

    @ColumnInfo(name = "quote_text")
    var quote: String = "",

    @ColumnInfo(name = "quote_author")
    var author: String = ""

)