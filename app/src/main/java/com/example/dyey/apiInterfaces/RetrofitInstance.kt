package com.example.dyey.apiInterfaces

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance {
    private val retrofit: Retrofit? = Retrofit.Builder()
        .baseUrl("http://3.232.30.130:3002/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ApiServices? = retrofit?.create(ApiServices::class.java)



}