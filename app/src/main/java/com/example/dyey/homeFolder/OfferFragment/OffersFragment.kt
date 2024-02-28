package com.example.dyey.homeFolder.OfferFragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dyey.ExtraClasses.MyPopupWindow
import com.example.dyey.R
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.databinding.FragmentOffersBinding
import com.example.dyey.homeFolder.HomeFragment.OnItemClickListener
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.CreateOffer
import com.example.dyey.homeFolder.OfferFragment.DeleteOffer.DeleteOfferDataClass
import com.example.dyey.homeFolder.OfferFragment.DeleteOffer.DeleteOfferResponseDataClass
import com.example.dyey.homeFolder.OfferFragment.EditOffer.EditOffer
import com.example.dyey.homeFolder.OfferFragment.EditOffer.EditOfferResponse
import com.example.dyey.homeFolder.OfferFragment.RestaurantDetail.RestaurantAbout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OffersFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentOffersBinding
    private lateinit var adapter: offersAdapter
    private var offerList: ArrayList<OfferDetails> = ArrayList()
    private val retrofitInstance = RetrofitInstance()
    private var apiService: ApiServices? = retrofitInstance.apiService
    private var call: Call<OffersDataClass>? = null
    private lateinit var sharedPreferences: SharedPreferences

    var offerId=0



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
        sharedPreferences = context?.getSharedPreferences("SelectedCards", Context.MODE_PRIVATE)!!

        try {
            getOfferDetails()
        }catch (e:Exception){
            e.printStackTrace()
        }
        binding.createoffers.setOnClickListener(){
            startActivity(Intent(context,CreateOffer::class.java))
        }

    }

    override fun onStop() {
        super.onStop()
        // Cancel the API call when the fragment is stopped
        call?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Cancel the API call when the fragment's view is destroyed
        call?.cancel()
    }

    private fun getOfferDetails() {
        apiService?.getOfferDetails("Bearer ${AppInfo.getToken()}")
            ?.enqueue(object : Callback<OffersDataClass> {
                override fun onResponse(call: Call<OffersDataClass>, response: Response<OffersDataClass>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.offerDetails?.let { offerDetails ->
                            if (offerDetails.isEmpty()) {
                                Log.d("OfferDetails1", offerDetails.toString()) }
                            else {
                                offerList= responseBody.offerDetails!!
                                val currentUserID = AppInfo.getUserDeviceId()?.toInt() // Assuming this method exists
                                val filteredOffers = offerDetails.filter { it.userId == currentUserID }

                                if (filteredOffers.isEmpty()) {
                                    Log.d("OfferDetails2", responseBody.offerDetails.toString())
                                } else {
                                    Log.d("OfferDetails3", "Filtered Offers: $filteredOffers")
                                    try {
                                        setUiForRecyclerView(ArrayList(filteredOffers))

                                    }catch (e:Exception){
                                        Log.d("offers",e.toString())
                                    }
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

    private fun deleteOfferDetails(offerId: Int) {
        val request = DeleteOfferDataClass(
            offerId = offerId
        )
        apiService?.deleteOffer("Bearer ${AppInfo.getToken()}",request)
            ?.enqueue(object : Callback<DeleteOfferResponseDataClass> {
                override fun onResponse(call: Call<DeleteOfferResponseDataClass>, response: Response<DeleteOfferResponseDataClass>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if(responseBody?.status==true){

                            getOfferDetails()
                            Log.d("Offers1", response.toString())

                        }else{
                            Log.d("Offers1", responseBody?.message.toString())
                            Log.d("Offers1", responseBody?.status.toString())

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

                override fun onFailure(call: Call<DeleteOfferResponseDataClass>, t: Throwable) {
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





    private fun setUiForRecyclerView(offerList: ArrayList<OfferDetails>) {
    binding.offers.layoutManager = LinearLayoutManager(requireContext())
    adapter = offersAdapter(offerList,this)
    binding.offers.adapter = adapter
    Log.d("recycler","data is coming")
}

    override fun onItemClick(position: Int) {
        Log.d("OffersFragment__",position.toString())
        Log.d("OffersFragment__",offerList.toString())
        val anchorView: View = binding.root.findViewById(R.id.anchor_view)
            val popupWindow = MyPopupWindow(requireContext())
            popupWindow.setOnDeleteClickListener {
                offerId = offerList[position].offerId ?: 0 // Assuming offerId is nullable
            deleteOfferDetails(offerId)
                Log.e("OffersFragment", offerId.toString())

            }
        popupWindow.restaurantDetails {
            offerId = offerList[position].offerId ?: 0 // Assuming offerId is nullable
            val intent=Intent(context,RestaurantAbout::class.java)
            intent.putExtra("name",offerList[position].restaurantName.toString())
            intent.putExtra("address",offerList[position].restaurantAddress.toString())
            intent.putExtra("rating",offerList[position].restaurantRatings.toString())
            intent.putExtra("status",offerList[position].restaurantStatus.toString())
            startActivity(intent)
            Log.e("id", offerId.toString())

        }
        popupWindow.restaurantEditDetails {
            offerId = offerList[position].offerId ?: 0
            saveOfferId(offerId)
            startActivity(Intent(context,CreateOffer::class.java))
        }
            popupWindow.show(anchorView)
            Log.e("OffersFragment", "Offer list is empty or position is out of bounds")
        Log.e("OffersFragment", offerId.toString())


    }

    private fun saveOfferId(offerId: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("offerId", offerId)
        editor.apply()
    }


//    override fun onItemClick(position: Int) {
//        val anchorView: View = binding.root.findViewById(R.id.anchor_view)
//        val popupWindow = MyPopupWindow(requireContext())
//        popupWindow.setOnDeleteClickListener {
//            val offerId = offerList[position].offerId ?: 0 // Assuming offerId is nullable
//            deleteOfferDetails(offerId)
//        }
//        popupWindow.show(anchorView)
//    }


}
