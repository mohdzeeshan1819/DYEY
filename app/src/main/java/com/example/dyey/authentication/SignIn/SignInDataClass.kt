package com.example.dyey.authentication.SignIn

data class SignInDataClass(
    val email: String,
    val password: String,
    val device_id: String,
    val firebase_token: String,
    val device_type: String
)
data class SignInResponse(
    val status: Boolean,
    val message: String,
    val token: String?,
    val user_id: Int?,
    val gender: String?,
    val multiple_images_upload_status: Int?,
    val profile_image_url: String?,
    val user_name: String?
)


