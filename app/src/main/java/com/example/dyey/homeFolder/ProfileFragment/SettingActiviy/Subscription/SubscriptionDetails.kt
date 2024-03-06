package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.Subscription

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dyey.R
import com.example.dyey.databinding.ActivitySubscriptionDetailsBinding
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.SettingActivity

class SubscriptionDetails : AppCompatActivity() {
    private lateinit var binding:ActivitySubscriptionDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySubscriptionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }
}