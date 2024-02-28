package com.example.dyey.apiInterfaces.UploadImage

import com.example.dyey.apiInterfaces.ApiServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UploadImageInstance {
    private val retrofit: Retrofit? = Retrofit.Builder()
        .baseUrl("http://34.225.58.45:8003/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ApiServices? = retrofit?.create(ApiServices::class.java)

}