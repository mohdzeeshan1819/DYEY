package com.example.dyey.homeFolder.OfferFragment.CreateOffer.OfferEndActivty

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.dyey.R
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.RestaurantDetails.RestaurantDetails
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.SearchByCuisin

class OfferEnd : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_end)
        val sharedPreferences = getSharedPreferences("SelectedCards", Context.MODE_PRIVATE)
        val selectedRestaurantName = sharedPreferences.getString("SELECTED_RESTAURANT_NAME", "")
        val selectedRestaurantAddress = sharedPreferences.getString("SELECTED_RESTAURANT_ADDRESS", "")
        val selectedText = sharedPreferences.getString("firstCard", "")

        val vicnity = sharedPreferences.getString("vicnity", "")


        Log.d("Selected Restaurant", "Name: $selectedRestaurantName, Address: $selectedRestaurantAddress")



        val restaurantName=findViewById<TextView>(R.id.restraName)
        val address=findViewById<TextView>(R.id.address)
        val food=findViewById<TextView>(R.id.foottype)
        val select=findViewById<TextView>(R.id.select)
        val back=findViewById<TextView>(R.id.back)
        back.setOnClickListener(){
            startActivity(Intent(this, RestaurantDetails::class.java))
            finish()
        }



        restaurantName.text=selectedRestaurantName.toString()
        address.text=vicnity.toString()
        food.text=selectedText.toString()
        select.setOnClickListener(){
            val editor = sharedPreferences.edit()
            editor.putString("SELECTED_RESTAURANT_NAME", restaurantName.text.toString())
            editor.putString("SELECTED_RESTAURANT_ADDRESS", address.text.toString())
            editor.putString("vicnity", food.text.toString())
            editor.apply()

            startActivity(Intent(this,SubmitOffers::class.java))
        }

    }



}