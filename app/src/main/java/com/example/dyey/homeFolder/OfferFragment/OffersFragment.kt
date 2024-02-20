package com.example.dyey.homeFolder.OfferFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.databinding.FragmentOffersBinding
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.CreateOffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OffersFragment : Fragment() {

    private lateinit var binding: FragmentOffersBinding
    private lateinit var adapter: offersAdapter
    private val offerList: ArrayList<OfferDetails> = ArrayList()
    private val retrofitInstance = RetrofitInstance()
    private var apiService: ApiServices? = retrofitInstance.apiService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOffersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiService = RetrofitInstance().apiService
        getOfferDetails()
        binding.createoffers.setOnClickListener(){
            startActivity(Intent(context,CreateOffer::class.java))
        }

    }



    private fun getOfferDetails() {
        apiService?.getOfferDetails("Bearer ${AppInfo.getToken()}")
            ?.enqueue(object : Callback<OffersDataClass> {
                override fun onResponse(call: Call<OffersDataClass>, response: Response<OffersDataClass>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.offerDetails?.let { offerDetails ->
                            if (offerDetails.isEmpty()) {
                                Log.d("OfferDetails", "No offer details found")
                            } else {
                                // Filter the offer details based on user ID
                                val currentUserID = AppInfo.getUserDeviceId()?.toInt() // Assuming this method exists
                                val filteredOffers = offerDetails.filter { it.userId == currentUserID }
                                if (filteredOffers.isEmpty()) {
                                    Log.d("OfferDetails", "No offers found for current user")
                                } else {
                                    Log.d("OfferDetails", "Filtered Offers: $filteredOffers")
                                    setUiForRecyclerView(ArrayList(filteredOffers))
                                }
                            }
                        }
                    } else {
                        // Handle unsuccessful response
                        Log.d("Offers", "Failed to fetch offer details")
                        Toast.makeText(
                            requireContext(),
                            "Failed to fetch offer details",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<OffersDataClass>, t: Throwable) {
                    // Handle network failure
                    Log.d("Offers", "Network error: ${t.message}")
                    Toast.makeText(
                        requireContext(),
                        "Network error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }


//    private fun setUiForRecyclerView(apiResponse: ArrayList<OffersDataClass>) {
//        binding.offers.layoutManager = LinearLayoutManager(requireContext())
//        adapter = offersAdapter(apiResponse)
//        binding.offers.adapter = adapter
//        Log.d("recycler","data is coming")
//    }
private fun setUiForRecyclerView(offerList: ArrayList<OfferDetails>) {
    binding.offers.layoutManager = LinearLayoutManager(requireContext())
    adapter = offersAdapter(offerList)
    binding.offers.adapter = adapter
    Log.d("recycler","data is coming")
}


}
