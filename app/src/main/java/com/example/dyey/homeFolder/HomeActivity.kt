package com.example.dyey.homeFolder

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dyey.R
import com.example.dyey.authentication.SignUp.SignUpDataClass
import com.example.dyey.homeFolder.HomeFragment.HomeFragment
import com.example.dyey.homeFolder.MessageFragment.MessageFragment
import com.example.dyey.homeFolder.OfferFragment.OffersFragment
import com.example.dyey.homeFolder.SettingFragment.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val userData = getUserDataFromSharedPreferences()

        // Now you can use userData in your HomeActivity
        // Example: display the first name in a TextView
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.offer -> {
                    loadFragment(OffersFragment())
                    true
                }
                R.id.message -> {
                    loadFragment(MessageFragment())
                    true
                }
                R.id.settings -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private fun getUserDataFromSharedPreferences(): SignUpDataClass? {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)

        // Retrieve JSON string from SharedPreferences
        val userDataJson = sharedPreferences.getString("user_data", "") ?: ""

        // Convert JSON string to UserData object
        val gson = Gson()
        return gson.fromJson(userDataJson, SignUpDataClass::class.java)
    }
}
