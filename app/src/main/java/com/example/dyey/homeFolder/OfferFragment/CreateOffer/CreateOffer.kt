package com.example.dyey.homeFolder.OfferFragment.CreateOffer


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.databinding.ActivityCreateOfferBinding

class CreateOffer : AppCompatActivity() {

    private lateinit var binding: ActivityCreateOfferBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var selectedFirstCard: View? = null
    private var selectedSecondCard: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppInfo.setContext(this)
        sharedPreferences = getSharedPreferences("SelectedCards", Context.MODE_PRIVATE)

        binding.brunch.setOnClickListener { selectFirstCard(binding.brunch) }
        binding.lunch.setOnClickListener { selectFirstCard(binding.lunch) }
        binding.dinner.setOnClickListener { selectFirstCard(binding.dinner) }
        binding.good.setOnClickListener { selectSecondCard(binding.good) }
        binding.great.setOnClickListener { selectSecondCard(binding.great) }
        binding.amazing.setOnClickListener { selectSecondCard(binding.amazing) }

        binding.SignIn.setOnClickListener { saveAndNavigate() }
    }

    private fun selectFirstCard(card: View) {
        resetFirstCardBackground()
        selectedFirstCard = card
        card.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_light))
    }

    private fun selectSecondCard(card: View) {
        resetSecondCardBackground()
        selectedSecondCard = card
        card.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_light))
    }

    private fun resetFirstCardBackground() {
        binding.brunch.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        binding.lunch.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        binding.dinner.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
    }

    private fun resetSecondCardBackground() {
        binding.good.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        binding.great.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        binding.amazing.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
    }

    private fun saveAndNavigate() {
        val selectedFirstText = when (selectedFirstCard) {
            binding.brunch -> binding.brunchtext.text.toString()
            binding.lunch -> binding.lunchtext.text.toString()
            binding.dinner -> binding.dinnertext.text.toString()
            else -> ""
        }

        val selectedSecondText = when (selectedSecondCard) {
            binding.good -> binding.goodtext.text.toString()
            binding.great -> binding.greattext.text.toString()
            binding.amazing -> binding.amazingtext.text.toString()
            else -> ""
        }

        saveSelectedCards(selectedFirstText, selectedSecondText)

        val intent = Intent(this, LocationCreate::class.java)
        startActivity(intent)
    }

    private fun saveSelectedCards(selectedFirstText: String, selectedSecondText: String) {
        val editor = sharedPreferences.edit()
        editor.putString("firstCard", selectedFirstText)
        editor.putString("secondCard", selectedSecondText)
        editor.apply()
    }
}
