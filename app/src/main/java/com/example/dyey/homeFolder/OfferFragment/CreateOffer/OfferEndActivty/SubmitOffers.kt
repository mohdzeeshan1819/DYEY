package com.example.dyey.homeFolder.OfferFragment.CreateOffer.OfferEndActivty

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.dyey.R
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.apiInterfaces.RetrofitInstanceTwo
import com.example.dyey.homeFolder.HomeActivity
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.RestaurantDetails.RestaurantDetails
import com.example.dyey.homeFolder.OfferFragment.EditOffer.EditOffer
import com.example.dyey.homeFolder.OfferFragment.EditOffer.EditOfferResponse
import com.example.dyey.homeFolder.OfferFragment.OfferDetails
import com.example.dyey.homeFolder.OfferFragment.OffersFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubmitOffers : AppCompatActivity() {

    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService
    private lateinit var sharedPreferences: SharedPreferences
    private var offerList: ArrayList<CreateOfferRequest> = ArrayList()
    private var editOfferList: ArrayList<EditOffer> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_offers)
        sharedPreferences = getSharedPreferences("SelectedCards", Context.MODE_PRIVATE)
        val restaurantName = findViewById<TextView>(R.id.name)
        val address = findViewById<TextView>(R.id.location)
        val food = findViewById<TextView>(R.id.cuisine)
        val createOffer = findViewById<TextView>(R.id.select)
        val back=findViewById<TextView>(R.id.back)
        back.setOnClickListener(){
            startActivity(Intent(this, OfferEnd::class.java))
            finish()
        }
        val latitude = sharedPreferences.getString(KEY_LATITUDE, null)?.toDoubleOrNull()
        val longitude = sharedPreferences.getString(KEY_LONGITUDE, null)?.toDoubleOrNull()

        restaurantName.text = sharedPreferences.getString("SELECTED_RESTAURANT_NAME", "")
        address.text = sharedPreferences.getString("SELECTED_RESTAURANT_ADDRESS", "")
        food.text = sharedPreferences.getString("vicnity", "")

        createOffer.setOnClickListener {
            if (latitude != null && longitude != null) {
                createOrUpdateOffer(latitude, longitude)
            } else {
                Log.d("latlongfinal", "lat:$latitude, long:$longitude")
            }
        }
    }

    private fun createOrUpdateOffer(latitude: Double, longitude: Double) {
        val selectedText = sharedPreferences.getString("firstCard", "") ?: ""
        val selectedLocation = sharedPreferences.getString("SELECTED_LOCATION", "") ?: ""

        val request = CreateOfferRequest(
            restaurant_name = sharedPreferences.getString("SELECTED_RESTAURANT_NAME", "").toString(),
            restaurant_lat = latitude,
            restaurant_long = longitude,
            restaurant_image = "https://bucket-dev-sss.s3.amazonaws.com/androfit/chat-videos/43a4efd1-860f-45c5-8308-89ed4525323d-ic_search_by_cuisin.png",
            restaurant_address = sharedPreferences.getString("vicnity", "").toString(),
            meal_type = selectedLocation,
            spending_amount = "$50-100",
            cuisines = selectedText,
            date = "2024-05-07",
            time = "13:30:00",
            day = "Sunday",
            attire = "Casual",
            intention = "Friendship",
            restaurant_ratings = 1,
            restaurant_status = "true"
        )

        val offerId = sharedPreferences.getInt("offerId", 0)
        if (offerId>0) {
            createOffer(request)
            editOfferDetails(offerId, request)
        } else {
            editOfferDetails(offerId, request)


        }
    }

    private fun createOffer(request: CreateOfferRequest) {
        apiService?.createOffer("Bearer ${AppInfo.getToken()}", request)?.enqueue(object : Callback<CreateOfferResponse> {
            override fun onResponse(call: Call<CreateOfferResponse>, response: Response<CreateOfferResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.status) {
                        // Offer created successfully
                        offerList.add(request)
                        navigateToOffersFragment()
                        Log.e("API Error", "HTTP Error: ${response.code()}")
                    } else {
                        // Handle API error response
                        Log.d("API Error", responseBody?.message ?: "Unknown error")
                        Toast.makeText(this@SubmitOffers, responseBody?.message.toString()+" that's why offer edited", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("API Error", "HTTP Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CreateOfferResponse>, t: Throwable) {
                // Handle network failure
                Log.e("Network Error", "Failed to make API call: ${t.message}", t)
            }
        })
    }

    private fun editOfferDetails(offerId: Int, newOffer: CreateOfferRequest) {
        val request = EditOffer(
            offerId = offerId,
            restaurantName = newOffer.restaurant_name,
            restaurantLat = newOffer.restaurant_lat,
            restaurantLong = newOffer.restaurant_long,
            mealType = newOffer.meal_type,
            spendingAmount = "$50-100",
            cuisines = newOffer.cuisines,
            date = newOffer.date,
            time = newOffer.time,
            day = newOffer.day,
            attire = "Casual",
            intention = "Friendship",
            restaurantRatings = 1,
            restaurantStatus = newOffer.restaurant_status.toString()
        )

        apiService?.editOffer("Bearer ${AppInfo.getToken()}", request)
            ?.enqueue(object : Callback<EditOfferResponse> {
                override fun onResponse(call: Call<EditOfferResponse>, response: Response<EditOfferResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody?.status == true) {
                            Log.d("Offers1", response.toString())
                            editOfferList.add(request)
                            navigateToOffersFragment()
                        } else {
                            Log.d("Offers1", responseBody?.message.toString())
                            Log.d("Offers1", responseBody?.status.toString())
                        }
                    } else {
                        // Handle unsuccessful response
                        Log.d("Offers", "Failed to fetch offer details")
                        Toast.makeText(
                            this@SubmitOffers,
                            "Failed to fetch offer details",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<EditOfferResponse>, t: Throwable) {
                    // Handle network failure
                    Log.d("Offers", "Network error: ${t.message}")
                    Toast.makeText(
                        this@SubmitOffers,
                        "Network error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun navigateToOffersFragment() {
//        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//        val offersFragment = OffersFragment()
//        fragmentTransaction.replace(R.id.select, offersFragment)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    companion object {
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }
}
