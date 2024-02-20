package com.example.dyey.authentication.ForgotPassword

import com.google.gson.annotations.SerializedName

data class VerifyOTPRequest(  @SerializedName("user_id" ) var userId : Int?    = null,
                              @SerializedName("code"    ) var code   : String? = null)

data class VerifyOTPResponse(
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("user_id" ) var userId  : Int?     = null)