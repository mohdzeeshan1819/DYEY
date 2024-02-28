package com.example.dyey.homeFolder.OfferFragment.DeleteOffer

import com.google.gson.annotations.SerializedName

data class DeleteOfferDataClass(@SerializedName("offer_id" ) var offerId : Int? = null)

data class DeleteOfferResponseDataClass(@SerializedName("status"  ) var status  : Boolean? = null,
                                        @SerializedName("message" ) var message : String?  = null)
