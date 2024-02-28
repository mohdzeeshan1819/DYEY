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
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.SearchByCuisin
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
//    private val KEY_LATITUDE = "LATITUDE"
//    private val KEY_LONGITUDE = "LONGITUDE"



    companion object {
        const val KEY_LATITUDE = "latitude"
        const val KEY_LONGITUDE = "longitude"
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resturant_details)
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("SelectedCards", Context.MODE_PRIVATE)

        // Retrieve the selected location from SharedPreferences
        val selectedLocation = sharedPreferences.getString("SELECTED_LOCATION", "")
        val restaurantName=findViewById<TextView>(R.id.resturantName)
        val back=findViewById<TextView>(R.id.back)
        restaurantName.text=selectedLocation.toString()

        back.setOnClickListener(){
            startActivity(Intent(this,SearchByCuisin::class.java))
            finish()
        }

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val latitude = sharedPreferences.getString(KEY_LATITUDE, null)?.toDoubleOrNull()
        val longitude = sharedPreferences.getString(KEY_LONGITUDE, null)?.toDoubleOrNull()
        if (latitude != null && longitude != null) {
            fetchRestaurantData(latitude, longitude)

        }
        Log.d("latlong","$latitude,$longitude")


    }

    private fun showRestaurantList(restaurants: ArrayList<Results>) {
        adapter = RestaurantAdapter(restaurants,this)
        recyclerView.adapter = adapter
    }
    private fun fetchRestaurantData(latitude: Double, longitude: Double) {

        val selectedText =  sharedPreferences.getString("SELECTED_LOCATION", "")
        val location = "$latitude,$longitude"
        val radius = 100000
        val type = "$selectedText"
        val keyword = "$selectedText"
        val apiKey = "AIzaSyAysVHVKFJTJe-LKZtH-PVlwX53XH-wuIw"

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
        // Save relevant data about the selected restaurant in SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("SELECTED_RESTAURANT_NAME", location.name.toString())
        editor.putString("SELECTED_RESTAURANT_ADDRESS", location.types.toString())
        editor.putString("vicnity", location.vicinity.toString())

        // Add more data as needed
        editor.apply()

        // Start the next activity
        val intent = Intent(this, OfferEnd::class.java)
        startActivity(intent)
    }

}
