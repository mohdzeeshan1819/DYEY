package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.ContactSupport

import com.google.gson.annotations.SerializedName

data class ContactSupportRequest(@SerializedName("user_id"     ) var userId      : Int?    = null,
                                 @SerializedName("topic"       ) var topic       : String? = null,
                                 @SerializedName("description" ) var description : String? = null)

data class ContactSupportResponce(@SerializedName("status"  ) var status  : Boolean? = null,
                                  @SerializedName("message" ) var message : String?  = null)