package com.example.dyey.homeFolder.ProfileFragment

import com.google.gson.annotations.SerializedName

data class ProfileDataClass (@SerializedName("status"  ) var status  : Boolean? = null,
                        @SerializedName("message" ) var message : String?  = null,
                        @SerializedName("user"    ) var user    : User?    = User())
data class User (

    @SerializedName("id"                 ) var id              : Int?              = null,
    @SerializedName("about"              ) var about           : String?           = null,
    @SerializedName("birthday"           ) var birthday        : String?           = null,
    @SerializedName("education"          ) var education       : String?           = null,
    @SerializedName("email"              ) var email           : String?           = null,
    @SerializedName("favorite_food"      ) var favoriteFood    : String?           = null,
    @SerializedName("first_name"         ) var firstName       : String?           = null,
    @SerializedName("gender"             ) var gender          : String?           = null,
    @SerializedName("height"             ) var height          : String?           = null,
    @SerializedName("last_name"          ) var lastName        : String?           = null,
    @SerializedName("profession"         ) var profession      : String?           = null,
    @SerializedName("profile_image_url"  ) var profileImageUrl : String?           = null,
    @SerializedName("sign"               ) var sign            : String?           = null,
    @SerializedName("admin_status"       ) var adminStatus     : String?           = null,
    @SerializedName("city"               ) var city            : String?           = null,
    @SerializedName("device_type"        ) var deviceType      : String?           = null,
    @SerializedName("latitude"           ) var latitude        : Double?           = null,
    @SerializedName("longitude"          ) var longitude       : Double?           = null,
    @SerializedName("state"              ) var state           : String?           = null,
    @SerializedName("password"           ) var password        : String?           = null,
    @SerializedName("age"                ) var age             : String?              = null,
    @SerializedName("premium_status"     ) var premiumStatus   : Int?              = null,
    @SerializedName("images"             ) var images          : ArrayList<String> = arrayListOf(),
    @SerializedName("id_proof_front_img" ) var idProofFrontImg : String?           = null,
    @SerializedName("id_proof_back_img"  ) var idProofBackImg  : String?           = null

)