package com.example.dyey.authentication.SignUp

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("status"            ) var status          : Boolean? = null,
    @SerializedName("message"           ) var message         : String?  = null,
    @SerializedName("token"             ) var token           : String?  = null,
    @SerializedName("user_id"           ) var userId          : Int?     = null,
    @SerializedName("gender"            ) var gender          : String?  = null,
    @SerializedName("profile_image_url" ) var profileImageUrl : String?  = null,
    @SerializedName("user_name"         ) var userName        : String?  = null

)
