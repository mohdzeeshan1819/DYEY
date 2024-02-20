package com.example.dyey.authentication.ForgotPassword

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("email" ) var email : String? = null

)
data class ForgotPasswordResponse(
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("user_id" ) var userId  : Int?     = null
)
