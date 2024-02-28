package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.ContactSupport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dyey.R
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.apiInterfaces.UploadImage.UploadImageInstance
import com.example.dyey.databinding.ActivityContactSupportBinding
import com.example.dyey.homeFolder.HomeActivity
import com.example.dyey.homeFolder.ProfileFragment.EditProfile.EditProfileRequest
import com.example.dyey.homeFolder.ProfileFragment.EditProfile.EditProfileResponse
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.SettingActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactSupport : AppCompatActivity() {
    private lateinit var binding:ActivityContactSupportBinding
    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityContactSupportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.login.setOnClickListener(){
            postdataapi()
        }
        binding.back.setOnClickListener(){
            startActivity(Intent(this@ContactSupport,SettingActivity::class.java))
            finish()
        }
    }

    private fun postdataapi() {

        val request= ContactSupportRequest()
        request.userId=AppInfo.getUserDeviceId()?.toInt()
        request.topic=binding.topic.text.toString()
        request.description=binding.description.text.toString()

        apiService?.contactSupport(request)?.enqueue(object : Callback<ContactSupportResponce> {
            override fun onResponse(
                call: Call<ContactSupportResponce>,
                response: Response<ContactSupportResponce>
            ) {
                if (response.isSuccessful) {
                    val responsebody=response.body()
                    if (responsebody?.status == true) {
                        Log.d("SignUpresponce", responsebody.toString())
                        startActivity(Intent(this@ContactSupport,HomeActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this@ContactSupport, response.toString(), Toast.LENGTH_SHORT).show()
                }
                Log.d("SignUpresponce", response.toString())
            }

            override fun onFailure(call: Call<ContactSupportResponce>, t: Throwable) {
                // Handle network failure
                Log.d("SignUp", t.toString())
                Toast.makeText(this@ContactSupport, "something went wrong in failure", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}