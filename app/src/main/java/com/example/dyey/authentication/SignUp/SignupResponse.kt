package com.example.dyey.authentication.SignUp

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("status"            ) var status          : Boolean? = null,
    @SerializedName("message"           ) var message         : String?  = null,
    @SerializedName("token"             ) var token           : String?  = null,
    @SerializedName("user_id"           ) var userId          : Int?     = null,
    @SerializedName("gender"            ) var gender          : String?  = null,
    @SerializedName("profile_image_url" ) var profileImageUrl : String?  = null,
    @SerializedName("user_name"         ) var userName        : String?  = null,
    @SerializedName("data")               var data    : SignUpDataClass?    = SignUpDataClass()


)

data class SignUpDataClass(
    @SerializedName("about"             ) var about           : String? = null,
    @SerializedName("birthday"          ) var birthday        : String? = null,
    @SerializedName("education"         ) var education       : String? = null,
    @SerializedName("email"             ) var email           : String? = null,
    @SerializedName("favorite_food"     ) var favoriteFood    : String? = null,
    @SerializedName("first_name"        ) var firstName       : String? = null,
    @SerializedName("gender"            ) var gender          : String? = null,
    @SerializedName("height"            ) var height          : String? = null,
    @SerializedName("last_name"         ) var lastName        : String? = null,
    @SerializedName("profession"        ) var profession      : String? = null,
    @SerializedName("profile_image_url" ) var profileImageUrl : String? = null,
    @SerializedName("sign"              ) var sign            : String? = null,
    @SerializedName("city"              ) var city            : String? = null,
    @SerializedName("device_id"         ) var deviceId        : String? = null,
    @SerializedName("firebase_token"    ) var firebaseToken   : String? = null,
    @SerializedName("device_type"       ) var deviceType      : String? = null,
    @SerializedName("latitude"          ) var latitude        : Double? = null,
    @SerializedName("longitude"         ) var longitude       : Double? = null,
    @SerializedName("state"             ) var state           : String? = null,
    @SerializedName("password"          ) var password        : String? = null,
    @SerializedName("age"               ) var age             : String? = null,
)


