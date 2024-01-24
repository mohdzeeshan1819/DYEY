package com.example.dyey.loginFolder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dyey.databinding.ActivityIntroductionBinding

class IntroductionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroductionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = IntroPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = adapter
        binding.next.setOnClickListener(){
            onNextButtonClick()
        }
        binding.previouse.setOnClickListener(){
            onPreviousButtonClick()
        }

    }
    fun onPreviousButtonClick() {
        val currentItem =  binding.viewPager.currentItem
        if (currentItem > 0) {
            binding.viewPager.setCurrentItem(currentItem - 1, true)
        }
    }

    fun onNextButtonClick() {
        try{
            val currentItem =  binding.viewPager.currentItem
            if (currentItem < (binding.viewPager.adapter?.count ?: (0 - 1))) {
                binding.viewPager.setCurrentItem(currentItem + 1, true)
            }
            else{
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
        } catch (e:Exception){

        }

    }
}

// IntroFragment1.kt
class IntroFragment1 : Fragment() {
    // Fragment content
}

// IntroFragment2.kt
class IntroFragment2 : Fragment() {
    // Fragment content
}
