package com.example.dyey.homeFolder.OfferFragment.RestaurantDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dyey.R
import com.example.dyey.databinding.ActivityRestaurantAboutBinding
import com.example.dyey.homeFolder.HomeActivity
import com.example.dyey.homeFolder.OfferFragment.OffersFragment

class RestaurantAbout : AppCompatActivity() {
    private lateinit var binding:ActivityRestaurantAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRestaurantAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.restraName.text=intent.getStringExtra("name")
        binding.status.text=intent.getStringExtra("status")
        binding.back.setOnClickListener(){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}