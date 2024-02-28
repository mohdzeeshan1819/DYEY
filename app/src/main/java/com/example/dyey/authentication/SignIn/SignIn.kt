package com.example.dyey.authentication.SignIn

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.authentication.ForgotPassword.ForgotPassword
import com.example.dyey.authentication.SignUp.SignUp
import com.example.dyey.databinding.ActivitySignInBinding
import com.example.dyey.homeFolder.HomeActivity
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val retrofitInstance = RetrofitInstance()
//    val isLoggedIn = getLoginState()
    private val apiService: ApiServices? = retrofitInstance.apiService
    private var deviceToken=""
    private var deviceId=""
    var email=""
    var pass=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppInfo.setContext(this)
        if (isUserLoggedIn()) {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
        init()

    }
    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    private fun saveLoginState(isLoggedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        editor.apply()
    }



    fun init(){
        try {
            binding.signUp.setOnClickListener(){
                startActivity(Intent(this,SignUp::class.java))
            }
        }catch (e:Exception){
            Log.d("SignIn",e.toString())
        }

        binding.SignIn.setOnClickListener(){
            validation()

        }
        binding.forgotPassword.setOnClickListener(){
            startActivity(Intent(this,ForgotPassword::class.java))

        }

    }

    private fun handleSignInSuccess(signInResponse: SignInResponse) {
        saveLoginState(true)
        AppInfo.setToken(signInResponse.token.toString())
         AppInfo.setEmail(email)
        AppInfo.setFcmToken(deviceToken)
        AppInfo.setUserDeviceId(deviceId)
        startActivity(Intent(this@SignIn, HomeActivity::class.java))
        finish()
    }
    private fun signIn(email: String, password: String) {
        val request = SignInDataClass(
            email = email,
            password = password,
            device_id = deviceId,
            firebase_token = deviceToken,
            device_type = "A"
        )

        apiService?.signIn(request)?.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                if (response.isSuccessful) {
                    val signInResponse: SignInResponse? = response.body()
                    if (signInResponse?.status == true) {

                        handleSignInSuccess(signInResponse)


                        try {
                            AppInfo.setLastName(signInResponse.user_name.toString())
                        } catch (e: Exception) {
                            Log.d("crashHandle", e.toString())
                        }

                        try {
                            AppInfo.setToken(signInResponse.token.toString())
                        } catch (e: Exception) {
                            Log.d("crashHandle", e.toString())
                        }

                        AppInfo.setUserDeviceId(signInResponse.user_id.toString())
                        startActivity(Intent(this@SignIn, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@SignIn,
                            "User does not exist. Please sign up.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@SignIn,
                        "Failed to sign in. Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Toast.makeText(
                    this@SignIn,
                    "Network error. Please check your internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun validation(){
        email = binding.email.text.trim().toString()
        pass = binding.password.text.trim().toString()
        if ( !email.contains("@") || !email.contains(".com") ) {
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show()
        }else if (!pass.contains("@") || pass.length<=5) {
            Toast.makeText(this, "Please enter correct password", Toast.LENGTH_SHORT).show()
        }else{
             email = binding.email.text.toString().trim()
             pass = binding.password.text.toString().trim()
            signIn(email,pass)

        }
    }

}