package com.example.dyey.apiInterfaces.UploadImage

import com.google.gson.annotations.SerializedName

data class UploadImageResponse(@SerializedName("status"     ) var status    : Boolean?          = null,
                               @SerializedName("message"    ) var message   : String?           = null,
                               @SerializedName("images_url" ) var imagesUrl : ArrayList<String> = arrayListOf())
