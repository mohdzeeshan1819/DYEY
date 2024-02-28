package com.example.dyey.authentication.ForgotPassword

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.databinding.ActivityVerificationBinding
import com.example.dyey.authentication.ForgotPassword.ChangePassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Verification : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService
    private lateinit var editTextList: List<EditText>
    private val MAX_OTP_LENGTH = 6

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
            binding.editText6
        )

        setupEditTextListeners()

        binding.SignIn.setOnClickListener {
            val enteredOtp = retrieveEnteredOtp()
            verifyOTP(enteredOtp)
            Log.d("verification",enteredOtp)
            Log.d("verification2",editTextList.toString())

        }
    }

    private fun setupEditTextListeners() {
        editTextList.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && index < editTextList.size - 1) {
                        editTextList[index + 1].requestFocus()
                    }
                }
            })
        }
    }

    private fun retrieveEnteredOtp(): String {
        val otpStringBuilder = StringBuilder()
        editTextList.forEach { editText ->
            otpStringBuilder.append(editText.text)
        }
        return otpStringBuilder.toString().trim()
    }

    private fun verifyOTP(enteredOtp: String) {
        val userID = intent.getStringExtra("userID")
        Log.d("userID",userID.toString())
        val request = VerifyOTPRequest(code = enteredOtp, userId = userID?.toInt())
        apiService?.verifyOTP(request)?.enqueue(object : Callback<VerifyOTPResponse> {
            override fun onResponse(
                call: Call<VerifyOTPResponse>,
                response: Response<VerifyOTPResponse>
            ) {
                if (response.isSuccessful) {
                    val verifyOTPResponse = response.body()!!
                    if (verifyOTPResponse.status == true) {
                        val userId = verifyOTPResponse.userId
                        val intent = Intent(this@Verification, ChangePassword::class.java)
                        intent.putExtra("userID", userId)
                        startActivity(intent)
                        finish()
                    } else {
                        val errorMessage = verifyOTPResponse.message
                        Toast.makeText(this@Verification, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@Verification, "Failed to verify OTP", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<VerifyOTPResponse>, t: Throwable) {
                Toast.makeText(this@Verification, "Failed to verify OTP", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
