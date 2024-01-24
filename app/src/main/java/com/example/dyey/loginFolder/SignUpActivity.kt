package com.example.dyey.loginFolder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.databinding.ActivitySignUpBinding
import com.example.dyey.homeFolder.HomeActivity
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding :ActivitySignUpBinding
    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices = retrofitInstance.apiService

    var first_name=""
    var last_name=""
    var gender=""
    var dob=""
    var education=""
    var height=""
    var profession=""
    var fav_food=""
    var about=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            startActivity(Intent(this,IntroductionActivity::class.java))
        }
        binding.login.setOnClickListener {
            validation()
//            saveUserDataToSharedPreferences()

        }
    }

    private fun saveUserDataToSharedPreferences() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Convert UserData object to JSON string
        val gson = Gson()
        val userDataJson = gson.toJson(editor)
        editor.putString("user_data", userDataJson)
        editor.apply()
    }

    private fun postdataapi() {

        val userData = SignUpDataClass(
            binding.fName.text.toString(),
            binding.lastName.text.toString(),
            binding.gender.text.toString(),
            binding.dob.text.toString(),
            binding.education.text.toString(),
            binding.height.text.toString(),
            binding.profession.text.toString(),
            binding.food.text.toString(),
           binding.aboutme.text.toString()
        )


        val call: Call<ResponseBody> = apiService.postData(userData)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // API call successful
                    saveUserDataToSharedPreferences()

                    // Navigate to HomeActivity
                    startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@SignUpActivity,"something went wrong in else block",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network failure
                Toast.makeText(this@SignUpActivity,"something went wrong in failure",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validation(){
        first_name = binding.fName.text.trim().toString()
        last_name = binding.lastName.text.trim().toString()
        gender = binding.gender.text.trim().toString()
        dob = binding.dob.text.trim().toString()
        education = binding.education.text.trim().toString()
        height = binding.height.text.trim().toString()
        profession = binding.profession.text.trim().toString()
        fav_food = binding.food.text.trim().toString()
        about = binding.aboutme.text.trim().toString()
        if(first_name.length<=3){
            Toast.makeText(this, "Please enter a valid first name", Toast.LENGTH_SHORT).show()
        } else if(last_name.length<=3){
            Toast.makeText(this, "Please enter a valid last name", Toast.LENGTH_SHORT).show()
        }else if(gender.length<=3){
            Toast.makeText(this, "Please enter a Male or Female with same writing", Toast.LENGTH_SHORT).show()
        } else if(dob.length<=8){
            Toast.makeText(this, "Please enter correct DOB", Toast.LENGTH_SHORT).show()
        }else if(education.length<=2){
            Toast.makeText(this, "Please enter correct education", Toast.LENGTH_SHORT).show()
        } else if(height<="2"){
            Toast.makeText(this, "Please enter correct height", Toast.LENGTH_SHORT).show()
        }else if(profession.length<=2){
            Toast.makeText(this, "Please enter correct profession", Toast.LENGTH_SHORT).show()
        }else if(fav_food.length<=2){
            Toast.makeText(this, "Please enter valid food name", Toast.LENGTH_SHORT).show()
        } else{
            startActivity(Intent(this,UploadID::class.java))
            finish()
        }

    }
}