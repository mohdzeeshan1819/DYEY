package com.example.dyey.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dyey.authentication.SignUp.SignUp
import com.example.dyey.databinding.ActivityUploadIdBinding
import com.example.dyey.homeFolder.HomeActivity

class UploadID : AppCompatActivity() {
    private lateinit var binding : ActivityUploadIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadIdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }
        binding.skip.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}