package com.example.dyey.homeFolder.HomeFragment.UserDetails

import com.google.gson.annotations.SerializedName

data class favRequest(@SerializedName("favorites_id" ) var favoritesId : Int? = null)
data class favResponse(@SerializedName("status"  ) var status  : Boolean? = null,
                       @SerializedName("message" ) var message : String?  = null)