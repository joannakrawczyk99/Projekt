package com.example.projecting.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class JsonApi(private val context: Context) {
    fun jsonApiService() : JsonApiService {

        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create(JsonApiService::class.java)
    }
    val BASE_URL ="https://jsonplaceholder.typicode.com"
}