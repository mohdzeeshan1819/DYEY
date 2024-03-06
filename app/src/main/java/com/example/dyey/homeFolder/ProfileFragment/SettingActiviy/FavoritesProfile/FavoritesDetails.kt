package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.FavoritesProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import com.example.dyey.R
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.databinding.ActivityFavoritesDetailsBinding
import com.example.dyey.homeFolder.HomeFragment.HomeAdapter
import com.example.dyey.homeFolder.HomeFragment.Users
import com.example.dyey.homeFolder.OfferFragment.OffersDataClass
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.SettingActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritesDetails : AppCompatActivity() {
    private lateinit var binding:ActivityFavoritesDetailsBinding
    private val retrofitInstance = RetrofitInstance()
    private var apiService: ApiServices? = retrofitInstance.apiService
    private val userList: ArrayList<UserDetails> = ArrayList()

    private lateinit var adapter: FavoritesDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFavoritesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener(){
            startActivity(Intent(this@FavoritesDetails,SettingActivity::class.java))
        }
        try {
            getOfferDetails()
        } catch (e:Exception){
            Log.d("error",e.toString())
        }
    }

    private fun getOfferDetails() {
        apiService?.getFavorite("Bearer ${AppInfo.getToken()}")
            ?.enqueue(object : Callback<FavoriteDataClass> {
                override fun onResponse(call: Call<FavoriteDataClass>, response: Response<FavoriteDataClass>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if(responseBody?.status==true){
                            responseBody.userDetails?.let { userList.addAll(it) }
                            recyclerview()
                            Log.d("data",responseBody.toString())

                        } else{
                            Log.d("else",responseBody.toString())
                        }

                    } else {
                        // Handle unsuccessful response
                        Log.d("Offers", "Failed to fetch offer details")
                        Toast.makeText(
                            this@FavoritesDetails,
                            "Failed to fetch offer details",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<FavoriteDataClass>, t: Throwable) {
                    // Handle network failure
                    Log.d("Offers", "Network error: ${t.message}")
                    Toast.makeText(
                       this@FavoritesDetails,
                        "Network error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun recyclerview(){
        adapter = FavoritesDetailsAdapter(this,userList)
        binding.favorite.adapter = adapter
        adapter.notifyDataSetChanged()
        Log.d("xeeshandata",adapter.toString())



    }

}