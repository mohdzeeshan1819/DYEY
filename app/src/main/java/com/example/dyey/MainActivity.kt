package com.example.dyey

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.dyey.authentication.SignIn.SignIn
import com.example.dyey.authentication.SignUp.SignUp
import com.example.dyey.homeFolder.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({

                val intent = Intent(this, SignIn::class.java)
                startActivity(intent)
                finish()

        }, 2000)
    }


}
