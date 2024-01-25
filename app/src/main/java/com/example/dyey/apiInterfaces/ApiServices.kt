package com.example.dyey.apiInterfaces

import com.example.dyey.authentication.SignUp.SignUpDataClass
import com.example.dyey.authentication.SignUp.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    @POST("/register")
    fun postData(@Body userData: SignUpDataClass): Call<SignupResponse>
}