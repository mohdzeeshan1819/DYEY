package com.example.dyey.apiInterfaces

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    val retrofit = Retrofit.Builder()
        .baseUrl("your_base_url")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiServices::class.java)

}