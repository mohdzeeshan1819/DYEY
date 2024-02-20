package com.example.dyey.homeFolder.OfferFragment

import com.google.gson.annotations.SerializedName

data class OffersDataClass(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("offer_details") var offerDetails: ArrayList<OfferDetails>? = null
)


data class OfferDetails (

    @SerializedName("offer_id"             ) var offerId            : Int?    = null,
    @SerializedName("user_id"              ) var userId             : Int?    = null,
    @SerializedName("direct_user_id"       ) var directUserId       : String? = null,
    @SerializedName("restaurant_name"      ) var restaurantName     : String? = null,
    @SerializedName("restaurant_lat"       ) var restaurantLat      : Double? = null,
    @SerializedName("restaurant_long"      ) var restaurantLong     : Double? = null,
    @SerializedName("restaurant_image"     ) var restaurantImage    : String? = null,
    @SerializedName("restaurant_address"   ) var restaurantAddress  : String? = null,
    @SerializedName("meal_type"            ) var mealType           : String? = null,
    @SerializedName("spending_amount"      ) var spendingAmount     : String? = null,
    @SerializedName("cuisines"             ) var cuisines           : String? = null,
    @SerializedName("date"                 ) var date               : String? = null,
    @SerializedName("time"                 ) var time               : String? = null,
    @SerializedName("day"                  ) var day                : String? = null,
    @SerializedName("accept_offer_status"  ) var acceptOfferStatus  : Int?    = null,
    @SerializedName("status"               ) var status             : Int?    = null,
    @SerializedName("attire"               ) var attire             : String? = null,
    @SerializedName("intention"            ) var intention          : String? = null,
    @SerializedName("restaurant_ratings"   ) var restaurantRatings  : Int?    = null,
    @SerializedName("restaurant_status"    ) var restaurantStatus   : String? = null,
    @SerializedName("date_time"            ) var dateTime           : String? = null,
    @SerializedName("premium_user_status"  ) var premiumUserStatus  : Int?    = null,
    @SerializedName("decline_offer_status" ) var declineOfferStatus : Int?    = null,
    @SerializedName("cancel_meet_status"   ) var cancelMeetStatus   : Int?    = null,
    @SerializedName("place_id"             ) var placeId            : String? = null,
    @SerializedName("is_confirm"           ) var isConfirm          : String? = null,
    @SerializedName("createdAt"            ) var createdAt          : String? = null,
    @SerializedName("updatedAt"            ) var updatedAt          : String? = null

)
