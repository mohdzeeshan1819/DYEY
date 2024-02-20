package com.example.dyey.homeFolder.HomeFragment

import com.google.gson.annotations.SerializedName

data class HomeDataClass(
    @SerializedName("status"  ) var status  : Boolean?         = null,
    @SerializedName("users"   ) var users   : ArrayList<Users> = arrayListOf(),
    @SerializedName("message" ) var message : String?          = null,
    @SerializedName("page"    ) var page    : Page?            = Page()
)

data class Page (

    @SerializedName("currentPage" ) var currentPage : Int? = null,
    @SerializedName("nextPage"    ) var nextPage    : Int? = null

)
data class Users (

    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("type"              ) var type            : String? = null,
    @SerializedName("about"             ) var about           : String? = null,
    @SerializedName("is_offer"          ) var isOffer         : Int?    = null,
    @SerializedName("birthday"          ) var birthday        : String? = null,
    @SerializedName("education"         ) var education       : String? = null,
    @SerializedName("email"             ) var email           : String? = null,
    @SerializedName("favorite_food"     ) var favoriteFood    : String? = null,
    @SerializedName("first_name"        ) var firstName       : String? = null,
    @SerializedName("gender"            ) var gender          : String? = null,
    @SerializedName("height"            ) var height          : String? = null,
    @SerializedName("firebase_id"       ) var firebaseId      : String? = null,
    @SerializedName("last_name"         ) var lastName        : String? = null,
    @SerializedName("profession"        ) var profession      : String? = null,
    @SerializedName("profile_image_url" ) var profileImageUrl : String? = null,
    @SerializedName("sign"              ) var sign            : String? = null,
    @SerializedName("admin_status"      ) var adminStatus     : String? = null,
    @SerializedName("city"              ) var city            : String? = null,
    @SerializedName("device_type"       ) var deviceType      : String? = null,
    @SerializedName("latitude"          ) var latitude        : Double? = null,
    @SerializedName("longitude"         ) var longitude       : Double? = null,
    @SerializedName("state"             ) var state           : String? = null,
    @SerializedName("password"          ) var password        : String? = null,
    @SerializedName("age"               ) var age             : Int?    = null,
    @SerializedName("distance"          ) var distance        : Int?    = null

)

data class HomeRequest(  @SerializedName("age"    ) var age    : ArrayList<Int> = arrayListOf(),
                         @SerializedName("miles"  ) var miles  : ArrayList<Int> = arrayListOf(),
                         @SerializedName("filter" ) var filter : String?           = null)
