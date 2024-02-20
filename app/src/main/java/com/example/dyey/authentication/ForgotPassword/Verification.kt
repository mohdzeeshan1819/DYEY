package com.example.dyey.authentication.ForgotPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.databinding.ActivityVerificationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Verification : AppCompatActivity() {
    private lateinit var binding:ActivityVerificationBinding
    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService
    private var enteredOtp = ""
    private lateinit var editTextList: List<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editTextList = listOf(
            binding.editText1,
            binding.editText2,
            binding.editText3,
            binding.editText4,
            binding.editText5,
            binding.editText6,

            )


        binding.SignIn.setOnClickListener {
            enteredOtp = retrieveEnteredOtp()
            verifyOTP(enteredOtp)
        }
        }

    private fun retrieveEnteredOtp(): String {
        val otpStringBuilder = StringBuilder()
        editTextList.forEach { editText ->
            otpStringBuilder.append(editText.text)
        }
        return otpStringBuilder.toString().trim()
    }


    private fun verifyOTP(enteredOtp:String) {
        val userID = intent.getStringExtra("userID")
        val request = VerifyOTPRequest(code = enteredOtp, userId = userID?.toInt())
        apiService?.verifyOTP(request)?.enqueue(object : Callback<VerifyOTPResponse> {
            override fun onResponse(
                call: Call<VerifyOTPResponse>,
                response: Response<VerifyOTPResponse>
            ) {
                if (response.isSuccessful) {
                    val verifyOTPResponse = response.body()!!
                    if (verifyOTPResponse.status == true) {
                        val userId=verifyOTPResponse.userId
                        val intent = Intent(this@Verification, ChangePassword::class.java)
                        intent.putExtra("userID", userId)
                        startActivity(intent)
                        finish()
                    } else {
                        val errorMessage = verifyOTPResponse.message
                        Log.d("VerifyOTP1", errorMessage.toString())
                        Toast.makeText(this@Verification, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("VerifyOTP2", "Failed to verify OTP: ${response.message()}")
                    Toast.makeText(this@Verification, "Failed to verify OTP", Toast.LENGTH_SHORT)
                        .show()

                }
            }

            override fun onFailure(call: Call<VerifyOTPResponse>, t: Throwable) {
                Log.d("VerifyOTP3", "Failed to verify OTP: ${t.message}")
                Log.d("otp",enteredOtp)
                Toast.makeText(this@Verification, "Failed to verify OTP", Toast.LENGTH_SHORT).show()
            }
        })
    }

}