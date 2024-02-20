package com.example.dyey.homeFolder.HomeFragment.UserDetails

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.dyey.R
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.databinding.ActivitySignInBinding
import com.example.dyey.databinding.ActivityUsersProfileBinding

import com.example.dyey.homeFolder.HomeFragment.Users
import com.google.gson.Gson

private const val PREF_NAME = "UserData"
private const val KEY_USER_DATA = "userData"

class UsersProfile : AppCompatActivity() {

    private lateinit var binding:ActivityUsersProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppInfo.setContext(this)
        getUserData()

    }
    fun getUserData() {
        val userId = intent.getIntExtra("USER_ID", 0) // Example for retrieving user ID
        val fullName = intent.getStringExtra("FULL_NAME")
        val about = intent.getStringExtra("ABOUT")
        val gender = intent.getStringExtra("GENDER")
        val education = intent.getStringExtra("EDUCATION")
        val sign = intent.getStringExtra("SIGN")
        val longitude = intent.getDoubleExtra("LONGITUDE", 0.0)
        val profession = intent.getStringExtra("PROFESSION")
        val favoriteFood = intent.getStringExtra("favfood")
        val profilepic = intent.getStringExtra("profilePic")
        val height = intent.getStringExtra("height")
        val status = intent.getStringExtra("status")


        binding.name.text=fullName.toString()
        binding.aboutme.text=about.toString()
        binding.gender.text=gender.toString()
        binding.education.text=education.toString()
        binding.location.text=longitude.toString()
        binding.Sign.text=sign.toString()
        binding.favFood.text=favoriteFood.toString()
        binding.occupation.text=profession.toString()
        binding.height.text=height.toString()
        binding.status.text=status.toString()
        Glide.with(this)
            .load(profilepic)
            .placeholder(R.drawable.ic_logo2)
            .error(R.drawable.ic_logo)
            .into(binding.profilePic)

    }
}