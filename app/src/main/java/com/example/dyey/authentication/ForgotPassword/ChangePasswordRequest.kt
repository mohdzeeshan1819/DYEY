package com.example.dyey.authentication.ForgotPassword

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(@SerializedName("id"       ) var id       : Int?    = null,
                                   @SerializedName("password" ) var password : String? = null)



data class ChangePasswordResponse(    @SerializedName("status"  ) var status  : Boolean? = null,
                                      @SerializedName("message" ) var message : String?  = null
)