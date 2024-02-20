package com.example.dyey.authentication.ForgotPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dyey.R
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.authentication.SignIn.SignInDataClass
import com.example.dyey.authentication.SignIn.SignInResponse
import com.example.dyey.databinding.ActivityChangePasswordBinding
import com.example.dyey.databinding.ActivitySignInBinding
import com.example.dyey.homeFolder.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassword : AppCompatActivity() {

    private lateinit var binding:ActivityChangePasswordBinding
    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService
    private var deviceToken=""
    private var deviceId=""
    private var id=""
    private var password=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppInfo.setContext(this)
        id = intent.getStringExtra("userID").toString()
        binding.SignIn.setOnClickListener(){
            this@ChangePassword.id = intent?.getIntExtra("userID",0).toString()
             validation()


        }
    }
    private fun signIn(id:Int,password: String) {
        val request = ChangePasswordRequest(id=id, password = password)

        apiService?.changePassword(request)?.enqueue(object : Callback<ChangePasswordResponse> {
            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>)
            {
                if (response.isSuccessful) {
                    val signInResponse: ChangePasswordResponse? = response.body()
                    if (signInResponse?.status==true) {


                        Log.d("crashHandle1", signInResponse.toString())
                        startActivity(Intent(this@ChangePassword, HomeActivity::class.java))
                        finish()
                    } else {
                        Log.d("crashHandle3", signInResponse.toString())

                        Toast.makeText(
                            this@ChangePassword,
                            "User does not exist. Please sign up.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.d("crashHandle4", response.toString())

                    Toast.makeText(
                        this@ChangePassword,
                        "Failed to sign in. Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                Log.d("crashHandle5", t.toString())

                Toast.makeText(
                    this@ChangePassword,
                    "Network error. Please check your internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun validation(){
        password = binding.confirmPass.text.trim().toString()
       val newpass = binding.newPassword.text.trim().toString()
        if(!newpass.contains("@") || newpass.length<=5){
            Toast.makeText(this, "Please enter valid password", Toast.LENGTH_SHORT).show()

        }else if (password != newpass) {
            Toast.makeText(this, "Confirm password not same as new password", Toast.LENGTH_SHORT).show()
        }else{
            if (id.isNotBlank()) {
                try {
                    this@ChangePassword.id = id.toInt().toString()
                    password=binding.confirmPass.text.toString().trim()
                    AppInfo.setPassword(password)
                    signIn(id.toInt(),password)
                } catch (e: NumberFormatException) {
                    // Handle the case where id is not a valid integer
                    Log.e("crashHandle", "Invalid userID format: $id")
                    // Optionally, show a toast message or perform other error handling
                }
            } else {
                // Handle the case where id is null or empty
                Log.e("crashHandle", "UserID is null or empty")
                // Optionally, show a toast message or perform other error handling
            }

        }
    }

}