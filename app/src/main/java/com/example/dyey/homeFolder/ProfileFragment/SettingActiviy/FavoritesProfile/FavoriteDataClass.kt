package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.FavoritesProfile

import com.google.gson.annotations.SerializedName

data class FavoriteDataClass( @SerializedName("status"       ) var status      : Boolean?               = null,
                              @SerializedName("user_details" ) var userDetails : ArrayList<UserDetails> = arrayListOf(),
                              @SerializedName("message"      ) var message     : String?                = null)


data class UserDetails (

    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("about"             ) var about           : String? = null,
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