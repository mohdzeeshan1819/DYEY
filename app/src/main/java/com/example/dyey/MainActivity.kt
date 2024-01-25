package com.example.dyey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.dyey.authentication.SignUp.SignUp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            // Start your main activity here
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}