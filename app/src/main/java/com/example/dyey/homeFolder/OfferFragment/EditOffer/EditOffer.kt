package com.example.dyey.homeFolder.OfferFragment.EditOffer

import com.google.gson.annotations.SerializedName

data class EditOffer(@SerializedName("offer_id"           ) var offerId           : Int?    = null,
                     @SerializedName("restaurant_name"    ) var restaurantName    : String? = null,
                     @SerializedName("restaurant_lat"     ) var restaurantLat     : Double? = null,
                     @SerializedName("restaurant_long"    ) var restaurantLong    : Double? = null,
                     @SerializedName("meal_type"          ) var mealType          : String? = null,
                     @SerializedName("spending_amount"    ) var spendingAmount    : String? = null,
                     @SerializedName("cuisines"           ) var cuisines          : String? = null,
                     @SerializedName("date"               ) var date              : String? = null,
                     @SerializedName("time"               ) var time              : String? = null,
                     @SerializedName("day"                ) var day               : String? = null,
                     @SerializedName("attire"             ) var attire            : String? = null,
                     @SerializedName("intention"          ) var intention         : String? = null,
                     @SerializedName("restaurant_ratings" ) var restaurantRatings : Int?    = null,
                     @SerializedName("restaurant_status"  ) var restaurantStatus  : String? = null)

data class EditOfferResponse(
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null)
