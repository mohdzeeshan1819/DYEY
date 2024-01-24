package com.example.dyey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.dyey.loginFolder.IntroductionActivity
import com.example.dyey.loginFolder.SignUpActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            // Start your main activity here
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}