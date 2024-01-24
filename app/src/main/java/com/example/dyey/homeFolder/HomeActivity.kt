package com.example.dyey.homeFolder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dyey.R
import com.example.dyey.loginFolder.SignUpDataClass
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val userData = getUserDataFromSharedPreferences()

        // Now you can use userData in your HomeActivity
        // Example: display the first name in a TextView
        val firstNameTextView: TextView = findViewById(R.id.skip)
        firstNameTextView.text = userData.firstName
    }
    private fun getUserDataFromSharedPreferences(): SignUpDataClass {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)

        // Retrieve JSON string from SharedPreferences
        val userDataJson = sharedPreferences.getString("user_data", "") ?: ""

        // Convert JSON string to UserData object
        val gson = Gson()
        return gson.fromJson(userDataJson, SignUpDataClass::class.java)
    }
}