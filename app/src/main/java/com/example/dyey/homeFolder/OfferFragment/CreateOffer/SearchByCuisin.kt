package com.example.dyey.homeFolder.OfferFragment.CreateOffer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.databinding.ActivitySearchByCuisinBinding
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.RestaurantDetails.RestaurantDetails

class SearchByCuisin : AppCompatActivity(), OnItemClickListeners {
    private lateinit var binding:ActivitySearchByCuisinBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchByCousinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySearchByCuisinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppInfo.setContext(this)

        sharedPreferences = getSharedPreferences("SelectedCards", Context.MODE_PRIVATE)

        val selectedText = sharedPreferences.getString("firstCard", "")
        val secondselectedText = sharedPreferences.getString("secondCard", "")
        binding.card1.text= selectedText.toString()
        binding.card2.text=secondselectedText.toString()

        recyclerView= binding.address
       recyclerView.layoutManager = LinearLayoutManager(this)

        // Manually create data
        val locations = listOf("All", "African", "American","Asian","BBQ","Caribbean","Chinese","French")

        adapter = SearchByCousinAdapter(locations,this)
        recyclerView.adapter = adapter

    }

    override fun onItemClick(location: String) {
        sharedPreferences.edit().putString("SELECTED_LOCATION", location).apply()
        val intent = Intent(this, RestaurantDetails::class.java)
        startActivity(intent)
    }
}