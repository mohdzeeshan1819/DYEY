package com.example.dyey.homeFolder.HomeFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.databinding.FragmentHomeBinding
import com.example.dyey.homeFolder.HomeFragment.UserDetails.UsersProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(),OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private val userList: ArrayList<Users> = ArrayList()
    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchHomeData()
    }
    private fun fetchHomeData() {
        val request = HomeRequest()
        request.age = ArrayList()
        request.miles = ArrayList()
        request.filter = ""

        apiService?.getHomeData("Bearer ${AppInfo.getToken()}",request)
            ?.enqueue(object : Callback<HomeDataClass> {
                override fun onResponse(
                    call: Call<HomeDataClass>,
                    response: Response<HomeDataClass>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let { homeData ->
                            if (homeData.status == true) {
                                userList.addAll(homeData.users)
                                setUiForRecyclerView()
                                Log.d("HomeData", responseBody.users.toString())

                            } else {
                                Log.d("HomeData", "No users found")
                            }
                        }
                    } else {
                        // Handle API error
                        Log.d("HomeData1", "Failed to fetch home data: ${response.message()}")
                        Toast.makeText(
                            requireContext(),
                            "Failed to fetch home data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<HomeDataClass>, t: Throwable) {
                    // Handle network error
                    Log.d("HomeData2", "Network error: ${t.message}")
                    Toast.makeText(
                        requireContext(),
                        "Network error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
    override fun onItemClick(position: Int) {
        val user = userList[position]
        val intent = Intent(context, UsersProfile::class.java)
        intent.putExtra("USER_ID", user.id)
        intent.putExtra("FULL_NAME",user.firstName+" "+user.lastName)
        intent.putExtra("ABOUT",user.about)
        intent.putExtra("GENDER",user.gender)
        intent.putExtra("EDUCATION",user.education)
        intent.putExtra("SIGN",user.sign)
        intent.putExtra("LONGITUDE",user.longitude)
        intent.putExtra("PROFESSION",user.profession)
        intent.putExtra("favfood",user.favoriteFood)
        intent.putExtra("profilePic",user.profileImageUrl)
        intent.putExtra("height",user.height)
        intent.putExtra("status",user.adminStatus)


        startActivity(intent)
    }

    private fun setUiForRecyclerView() {
        adapter = HomeAdapter(userList,this)
        binding.offers.adapter = adapter
    }


}
