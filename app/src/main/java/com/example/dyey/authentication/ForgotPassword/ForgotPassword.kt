package com.example.dyey.authentication.ForgotPassword

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.se.omapi.Session
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.databinding.ActivityForgotPasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.Authenticator
import java.net.PasswordAuthentication
import java.util.Properties

class ForgotPassword : AppCompatActivity() {
    private lateinit var binding:ActivityForgotPasswordBinding
    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService
    var email=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppInfo.setContext(this)

      init()
    }
    fun init(){
        binding.SignIn.setOnClickListener(){
            email = binding.email.text.toString().trim()
            forgotPassword(email)
            Log.d("signIn","done")
        }

    }

    private fun forgotPassword(email: String){
        val request = ForgotPasswordRequest(email = email)
         apiService?.forgotPassword(request)?.enqueue(object : Callback<ForgotPasswordResponse> {
            override fun onResponse(call: Call<ForgotPasswordResponse>, response: Response<ForgotPasswordResponse>) {
                if (response.isSuccessful) {
                    val forgotPasswordResponse = response.body()!!
                    if (forgotPasswordResponse.status!!) {

                        val userID = forgotPasswordResponse.userId.toString()
                        Log.d("forgotpass1", forgotPasswordResponse.status.toString())
                        Log.d("forgotpass2", userID)
                        val intent = Intent(this@ForgotPassword, Verification::class.java)
                        intent.putExtra("userID", userID)
                        Log.d("userID1",userID.toString())

                        startActivity(intent)
                        finish()


                    } else {
                        val message = forgotPasswordResponse.message
                        Log.d("forgotpass3",forgotPasswordResponse.message.toString())

                    }
                } else {
                 Log.d("forgotpass4",response.message().toString())
                }
            }

            override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                Log.d("onfalure",t.message.toString())

            }
        })
    }

}