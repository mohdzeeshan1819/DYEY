package com.example.dyey.apiInterfaces

import com.example.dyey.loginFolder.SignUpDataClass
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface ApiServices {
    @POST("your_api_endpoint")
    fun postData(@Body userData: SignUpDataClass): Call<ResponseBody>
}