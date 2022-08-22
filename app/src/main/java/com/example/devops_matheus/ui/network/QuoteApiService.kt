package com.example.devops_matheus.ui.network

import com.example.devops_matheus.ui.database.quote.Quote
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://zenquotes.io/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface QuoteApiService {
    @GET("random")
    fun getQuoteAsync(): Deferred<Quote>
}

object QuoteApi {
    val retrofitService: QuoteApiService by lazy {
        retrofit.create(QuoteApiService::class.java)
    }
}