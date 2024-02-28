package com.example.dyey.homeFolder.ProfileFragment.EditProfile

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(@SerializedName("about"              ) var about             : String? = null,
                              @SerializedName("birthday"           ) var birthday          : String? = null,
                              @SerializedName("education"          ) var education         : String? = null,
                              @SerializedName("favorite_food"      ) var favoriteFood      : String? = null,
                              @SerializedName("first_name"         ) var firstName         : String? = null,
                              @SerializedName("gender"             ) var gender            : String? = null,
                              @SerializedName("height"             ) var height            : String? = null,
                              @SerializedName("last_name"          ) var lastName          : String? = null,
                              @SerializedName("profession"         ) var profession        : String? = null,
                              @SerializedName("city"               ) var city              : String? = null,
                              @SerializedName("latitude"           ) var latitude          : Int?    = null,
                              @SerializedName("longitude"          ) var longitude         : Int?    = null,
                              @SerializedName("state"              ) var state             : String? = null,
                              @SerializedName("profile_visibility" ) var profileVisibility : Int?    = null,
                              @SerializedName("profile_image_url"  ) var profileImageUrl   : String? = null,
                              @SerializedName("id_proof_front_img" ) var idProofFrontImg   : String? = null,
                              @SerializedName("id_proof_back_img"  ) var idProofBackImg    : String? = null)

data class EditProfileResponse( @SerializedName("status"  ) var status  : Boolean? = null,
                                @SerializedName("message" ) var message : String?  = null)