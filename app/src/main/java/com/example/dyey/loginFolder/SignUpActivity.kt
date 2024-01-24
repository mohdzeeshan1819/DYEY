package com.example.dyey.loginFolder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dyey.R
import com.example.dyey.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding :ActivitySignUpBinding
    var first_name=""
    var last_name=""
    var gender=""
    var dob=""
    var education=""
    var height=""
    var profession=""
    var fav_food=""
    var about=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            startActivity(Intent(this,IntroductionActivity::class.java))
        }
        binding.login.setOnClickListener {
            validation()
        }
    }


    private fun validation(){
        first_name = binding.fName.text.trim().toString()
        last_name = binding.lastName.text.trim().toString()
        gender = binding.gender.text.trim().toString()
        dob = binding.dob.text.trim().toString()
        education = binding.education.text.trim().toString()
        height = binding.height.text.trim().toString()
        profession = binding.profession.text.trim().toString()
        fav_food = binding.food.text.trim().toString()
        about = binding.aboutme.text.trim().toString()
        if(first_name.length<=3){
            Toast.makeText(this, "Please enter a valid first name", Toast.LENGTH_SHORT).show()
        } else if(last_name.length<=3){
            Toast.makeText(this, "Please enter a valid last name", Toast.LENGTH_SHORT).show()
        }else if(gender.length<=3){
            Toast.makeText(this, "Please enter a Male or Female with same writing", Toast.LENGTH_SHORT).show()
        } else if(dob.length<=8){
            Toast.makeText(this, "Please enter correct DOB", Toast.LENGTH_SHORT).show()
        }else if(education.length<=2){
            Toast.makeText(this, "Please enter correct education", Toast.LENGTH_SHORT).show()
        } else if(height<="2"){
            Toast.makeText(this, "Please enter correct height", Toast.LENGTH_SHORT).show()
        }else if(profession.length<=2){
            Toast.makeText(this, "Please enter correct profession", Toast.LENGTH_SHORT).show()
        }else if(fav_food.length<=2){
            Toast.makeText(this, "Please enter valid food name", Toast.LENGTH_SHORT).show()
        } else{
            startActivity(Intent(this,UploadID::class.java))
            finish()
        }

    }
}