package com.example.dyey.loginFolder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dyey.R
import com.example.dyey.databinding.ActivitySignUpBinding
import com.example.dyey.databinding.ActivityUploadIdBinding

class UploadID : AppCompatActivity() {
    private lateinit var binding : ActivityUploadIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadIdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
        binding.skip.setOnClickListener {
            startActivity(Intent(this,AddPhotos::class.java))
            finish()
        }
    }
}