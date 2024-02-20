package com.example.dyey.homeFolder.OfferFragment.CreateOffer.OfferEndActivty

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dyey.R

class OfferEnd : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_end)
        sharedPreferences = getSharedPreferences("SelectedCards", Context.MODE_PRIVATE)

        // Retrieve the selected location from SharedPreferences
        val selectedLocation = sharedPreferences.getString("SELECTED_LOCATION", "")



        val restaurantName=findViewById<TextView>(R.id.restraName)
        restaurantName.text=selectedLocation.toString()
    }
}