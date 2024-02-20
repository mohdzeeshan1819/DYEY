package com.example.dyey.homeFolder.OfferFragment.CreateOffer.RestaurantDetails

import com.google.gson.annotations.SerializedName

data class RestaurantDataClass(
    @SerializedName("html_attributions" ) var htmlAttributions : ArrayList<String>  = arrayListOf(),
    @SerializedName("next_page_token"   ) var nextPageToken    : String?            = null,
    @SerializedName("results"           ) var results          : ArrayList<Results> = arrayListOf(),
    @SerializedName("status"            ) var status           : String?            = null
)

data class Geometry (

    @SerializedName("location" ) var location : Location? = Location(),
    @SerializedName("viewport" ) var viewport : Viewport? = Viewport()

)

data class Location (

    @SerializedName("lat" ) var lat : Double? = null,
    @SerializedName("lng" ) var lng : Double? = null

)
data class Viewport (

    @SerializedName("northeast" ) var northeast : Northeast? = Northeast(),
    @SerializedName("southwest" ) var southwest : Southwest? = Southwest()

)
data class Northeast (

    @SerializedName("lat" ) var lat : Double? = null,
    @SerializedName("lng" ) var lng : Double? = null

)
data class Southwest (

    @SerializedName("lat" ) var lat : Double? = null,
    @SerializedName("lng" ) var lng : Double? = null

)

data class OpeningHours (

    @SerializedName("open_now" ) var openNow : Boolean? = null

)
data class Photos (

    @SerializedName("height"            ) var height           : Int?              = null,
    @SerializedName("html_attributions" ) var htmlAttributions : ArrayList<String> = arrayListOf(),
    @SerializedName("photo_reference"   ) var photoReference   : String?           = null,
    @SerializedName("width"             ) var width            : Int?              = null

)
data class PlusCode (

    @SerializedName("compound_code" ) var compoundCode : String? = null,
    @SerializedName("global_code"   ) var globalCode   : String? = null

)
data class Results (

    @SerializedName("business_status"       ) var businessStatus      : String?           = null,
    @SerializedName("geometry"              ) var geometry            : Geometry?         = Geometry(),
    @SerializedName("icon"                  ) var icon                : String?           = null,
    @SerializedName("icon_background_color" ) var iconBackgroundColor : String?           = null,
    @SerializedName("icon_mask_base_uri"    ) var iconMaskBaseUri     : String?           = null,
    @SerializedName("name"                  ) var name                : String?           = null,
    @SerializedName("opening_hours"         ) var openingHours        : OpeningHours?     = OpeningHours(),
    @SerializedName("photos"                ) var photos              : ArrayList<Photos> = arrayListOf(),
    @SerializedName("place_id"              ) var placeId             : String?           = null,
    @SerializedName("plus_code"             ) var plusCode            : PlusCode?         = PlusCode(),
    @SerializedName("price_level"           ) var priceLevel          : Int?              = null,
    @SerializedName("rating"                ) var rating              : Double?           = null,
    @SerializedName("reference"             ) var reference           : String?           = null,
    @SerializedName("scope"                 ) var scope               : String?           = null,
    @SerializedName("types"                 ) var types               : ArrayList<String> = arrayListOf(),
    @SerializedName("user_ratings_total"    ) var userRatingsTotal    : Int?              = null,
    @SerializedName("vicinity"              ) var vicinity            : String?           = null

)