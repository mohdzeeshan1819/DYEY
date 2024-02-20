package com.example.dyey.apiInterfaces

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstanceTwo {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ApiServices = retrofit.create(ApiServices::class.java)
}