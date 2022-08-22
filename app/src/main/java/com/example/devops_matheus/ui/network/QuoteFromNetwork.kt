package com.example.devops_matheus.ui.network

import com.example.devops_matheus.ui.database.quote.Quote
import com.squareup.moshi.Json

data class QuoteFromNetwork(

    @Json(name = "quote_text")
    val q: String,

    @Json(name = "quote_author")
    val a: String
)

fun QuoteFromNetwork.asDBModel(): Quote {
    return Quote(quote = q, author = a)
}