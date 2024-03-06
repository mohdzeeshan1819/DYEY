package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dyey.R
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.authentication.SignIn.SignIn
import com.example.dyey.authentication.SignUp.SignUp
import com.example.dyey.databinding.ActivitySettingBinding
import com.example.dyey.homeFolder.HomeActivity
import com.example.dyey.homeFolder.HomeFragment.HomeDataClass
import com.example.dyey.homeFolder.HomeFragment.HomeRequest
import com.example.dyey.homeFolder.ProfileFragment.EditProfile.EditProfile
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.ContactSupport.ContactSupport
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.FavoritesProfile.FavoritesDetails
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.Subscription.Subscription
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.TermAndCondition.TermAndCondition
import com.example.dyey.homeFolder.ProfileFragment.SettingsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySettingBinding

    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        // Handle the back button press
        startActivity(Intent(this@SettingActivity, HomeActivity::class.java))
        finish()
    }

    fun init(){
        binding.logout.setOnClickListener(){
            fetchHomeData()
        }
        binding.back.setOnClickListener(){
            onBackPressed()
        }
        binding.deleteAccount.setOnClickListener(){
            deleteAccount()
        }
        binding.myfav.setOnClickListener(){
            startActivity(Intent(this@SettingActivity,FavoritesDetails::class.java))
        }
        binding.termAndCondition.setOnClickListener(){
            val intent= Intent(this@SettingActivity,TermAndCondition::class.java)
            intent.putExtra("pageName","Term & Condition")
            startActivity(intent)        }
        binding.privacyPolicy.setOnClickListener(){
            val intent= Intent(this@SettingActivity,TermAndCondition::class.java)
            intent.putExtra("pageName","Privacy Policy")
            startActivity(intent)
        }
        binding.editProfile.setOnClickListener(){
            startActivity(Intent(this@SettingActivity,EditProfile::class.java))
        }
        binding.contactSupport.setOnClickListener(){
            startActivity(Intent(this@SettingActivity,ContactSupport::class.java))
        }
        binding.subscription.setOnClickListener(){
            startActivity(Intent(this@SettingActivity, Subscription::class.java))
        }

    }

    private fun logout() {
        saveLoginState(false)
        startActivity(Intent(this, SignIn::class.java))
    }

    private fun saveLoginState(isLoggedIn: Boolean) {
        val sharedPreferences = this.getSharedPreferences("login_state", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (isLoggedIn) {
            editor.putBoolean("is_logged_in", true)
        } else {
            editor.clear() // Clear all data when logging out
        }
        editor.apply()
    }

    private fun deleteAccount(){
        apiService?.deleteAccount("Bearer ${AppInfo.getToken()}")?.enqueue(object : Callback<DeleteAccountResponse>{
            override fun onResponse(
                call: Call<DeleteAccountResponse>,
                response: Response<DeleteAccountResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody: DeleteAccountResponse? =response.body()
                    if(responseBody?.status==true){
                        saveLoginState(false)
                        startActivity(Intent(this@SettingActivity, SignUp::class.java))
                        finish()

                    } else{
                        Log.d("notdelete",responseBody.toString())
                    }
                } else{
                    Log.d("notdelete2",response.toString())
                }
            }

            override fun onFailure(call: Call<DeleteAccountResponse>, t: Throwable) {
                Log.d("notdelete3",t.toString())

            }

        })
    }


    private fun fetchHomeData() {
        val request = LogoutRequestDataClass()
        request.deviceId = AppInfo.getUserDeviceId().toString()
        Log.d("deviceid",request.deviceId.toString())


        apiService?.logout("Bearer ${AppInfo.getToken()}",request)
            ?.enqueue(object : Callback<LogoutResponse> {
                override fun onResponse(
                    call: Call<LogoutResponse>,
                    response: Response<LogoutResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let { homeData ->
                            if (homeData.status == true) {
                                logout()
                                Log.d("HomeData", responseBody.toString())

                            } else {
                                Log.d("HomeData", "No users found")
                            }
                        }
                    } else {
                        // Handle API error
                        Log.d("HomeData1", "Failed to fetch home data: ${response.message()}")
                        Toast.makeText(
                            this@SettingActivity,
                            "Failed to fetch home data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    // Handle network error
                    Log.d("HomeData2", "Network error: ${t.message}")
                    Toast.makeText(
                        this@SettingActivity,
                        "Network error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

}