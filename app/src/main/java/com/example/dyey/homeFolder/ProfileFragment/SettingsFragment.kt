package com.example.dyey.homeFolder.ProfileFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.dyey.R
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.authentication.SignIn.SignIn
import com.example.dyey.databinding.FragmentSettingsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class SettingsFragment : Fragment() {

    private lateinit var binding:FragmentSettingsBinding
    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService
    private var deviceToken=""
    private var deviceId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchProfileData()
        binding.logout.setOnClickListener(){
            logout()
        }

    }

    private fun logout() {
        // Clear the login state
        saveLoginState(false)
        startActivity(Intent(requireContext(), SignIn::class.java))
        requireActivity().finish()
    }

    private fun saveLoginState(isLoggedIn: Boolean) {
        val sharedPreferences = requireContext().getSharedPreferences("login_state", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        editor.apply()
    }
    private fun fetchProfileData() {

        apiService?.getProfile("Bearer ${AppInfo.getToken()}")
            ?.enqueue(object : Callback<ProfileDataClass> {
                override fun onResponse(
                    call: Call<ProfileDataClass>,
                    response: Response<ProfileDataClass>
                ) {
                    if (response.isSuccessful) {
                        val profileData: ProfileDataClass? = response.body()
                        if (profileData!!.status == true) {

                                val image = profileData.user?.profileImageUrl.toString()
                                Glide.with(requireContext())// Context or Fragment reference
                                    .load(image)
                                    .placeholder(R.drawable.ic_logo2) // Replace default_image with the ID of your default image resource
                                    .into(binding.profilePic)
                                binding.name.text = profileData.user?.lastName.toString()
                                binding.aboutme.text = profileData.user?.about.toString()
                                binding.education.text = profileData.user?.education.toString()
                                binding.Sign.text = profileData.user?.sign.toString()
                                binding.location.text = profileData.user?.city.toString()
                                binding.height.text = profileData.user?.height.toString()
                                binding.occupation.text = profileData.user?.profession.toString()
                                binding.favFood.text = profileData.user?.favoriteFood.toString()
                                binding.gender.text = profileData.user?.gender.toString()
                                Log.d("profileApi1", response.toString())
                                Log.d("profileApi2", profileData.toString())
                                Log.d("profileApi3", profileData.user.toString())


                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Something went wrong in on response",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("homeerror1", response.toString())
                        Log.d("homeerror2", AppInfo.getToken().toString())
                        Log.d("homeerror3", AppInfo.getIsLoggedIn().toString())
                        Log.d("homeerror4", AppInfo.getAbout().toString())


                    }
                }

                override fun onFailure(call: Call<ProfileDataClass>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "Something went wrong in on failure",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("homeerror5", t.toString())

                }
            })
    }





}