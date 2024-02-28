package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy

import com.google.gson.annotations.SerializedName

data class LogoutRequestDataClass(  @SerializedName("device_id" ) var deviceId : String? = null

)

data class LogoutResponse(@SerializedName("status"  ) var status  : Boolean? = null,
                          @SerializedName("message" ) var message : String?  = null)
