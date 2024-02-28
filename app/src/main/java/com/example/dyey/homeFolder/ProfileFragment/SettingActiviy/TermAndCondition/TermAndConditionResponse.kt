package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.TermAndCondition

import com.google.gson.annotations.SerializedName

data class TermAndConditionResponse(
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("details" ) var details : Details? = Details())
data class Details (

    @SerializedName("id"        ) var id        : Int?    = null,
    @SerializedName("title"     ) var title     : String? = null,
    @SerializedName("content"   ) var content   : String? = null,
    @SerializedName("createdAt" ) var createdAt : String? = null,
    @SerializedName("updatedAt" ) var updatedAt : String? = null

)

data class TermAndConditionRequest(  @SerializedName("user_id" ) var userId : Int? = null)