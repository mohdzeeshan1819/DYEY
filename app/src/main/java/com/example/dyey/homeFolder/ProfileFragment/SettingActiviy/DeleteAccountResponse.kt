package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy

import com.google.gson.annotations.SerializedName

data class DeleteAccountResponse(  @SerializedName("status"  ) var status  : Boolean? = null,
                                   @SerializedName("message" ) var message : String?  = null)