package com.example.dyey.homeFolder.OfferFragment.CreateOffer.RestaurantDetails

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dyey.R
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.apiInterfaces.RetrofitInstanceTwo
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.OfferEndActivty.OfferEnd
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.OnItemClickListeners
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantDetails : AppCompatActivity(), OnItemClickListenerss {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RestaurantAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val restaurants: ArrayList<Results> = ArrayList()
    private val retrofitInstance = RetrofitInstanceTwo()
    private val apiService: ApiServices = retrofitInstance.apiService



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resturant_details)
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("SelectedCards", Context.MODE_PRIVATE)

        // Retrieve the selected location from SharedPreferences
        val selectedLocation = sharedPreferences.getString("SELECTED_LOCATION", "")



        val restaurantName=findViewById<TextView>(R.id.resturantName)
        restaurantName.text=selectedLocation.toString()

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchRestaurantData()

    }

    private fun showRestaurantList(restaurants: ArrayList<Results>) {
        adapter = RestaurantAdapter(restaurants,this)
        recyclerView.adapter = adapter
    }
    private fun fetchRestaurantData() {
        val location = "30.7333,76.7794"
        val radius = 100000
        val type = "Allrestaurant"
        val keyword = "dinner"
        val apiKey = "AIzaSyBnYM_Zz-icGMKAtSYHmiohQ3rD3V6lMEg"

        val call = apiService.getGoogleRestaurant(location, radius, type, keyword, apiKey)
        call.enqueue(object : Callback<RestaurantDataClass> {
            override fun onResponse(
                call: Call<RestaurantDataClass>,
                response: Response<RestaurantDataClass>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.status == "OK") {
                        showRestaurantList(responseBody.results)
                    } else {
                        Log.e("API Error1", "Failed to fetch restaurant data: Invalid response")
                    }
                } else {
                    Log.e("API Error2", "Failed to fetch restaurant data: ${response.message()}")
                    Toast.makeText(
                        this@RestaurantDetails,
                        "Failed to fetch restaurant data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<RestaurantDataClass>, t: Throwable) {
                Log.e("Network Error3", "Network error: ${t.message}")
                Toast.makeText(
                    this@RestaurantDetails,
                    "Network error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onItemClick(location: Results) {
        sharedPreferences.edit().putString("SELECTED_LOCATION", location.toString()).apply()
        val intent = Intent(this, OfferEnd::class.java)
        startActivity(intent)
    }
}
