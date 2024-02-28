package com.example.dyey.apiInterfaces

import com.example.dyey.apiInterfaces.UploadImage.UploadImageResponse
import com.example.dyey.authentication.ForgotPassword.ChangePasswordRequest
import com.example.dyey.authentication.ForgotPassword.ChangePasswordResponse
import com.example.dyey.authentication.ForgotPassword.ForgotPasswordRequest
import com.example.dyey.authentication.ForgotPassword.ForgotPasswordResponse
import com.example.dyey.authentication.ForgotPassword.VerifyOTPRequest
import com.example.dyey.authentication.ForgotPassword.VerifyOTPResponse
import com.example.dyey.authentication.SignIn.SignInDataClass
import com.example.dyey.authentication.SignIn.SignInResponse
import com.example.dyey.authentication.SignUp.SignupResponse
import com.example.dyey.homeFolder.HomeFragment.HomeDataClass
import com.example.dyey.homeFolder.HomeFragment.HomeRequest
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.OfferEndActivty.CreateOfferRequest
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.OfferEndActivty.CreateOfferResponse
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.RestaurantDetails.RestaurantDataClass
import com.example.dyey.homeFolder.OfferFragment.DeleteOffer.DeleteOfferDataClass
import com.example.dyey.homeFolder.OfferFragment.DeleteOffer.DeleteOfferResponseDataClass
import com.example.dyey.homeFolder.OfferFragment.EditOffer.EditOffer
import com.example.dyey.homeFolder.OfferFragment.EditOffer.EditOfferResponse
import com.example.dyey.homeFolder.OfferFragment.OffersDataClass
import com.example.dyey.homeFolder.ProfileFragment.EditProfile.EditProfileRequest
import com.example.dyey.homeFolder.ProfileFragment.EditProfile.EditProfileResponse
import com.example.dyey.homeFolder.ProfileFragment.ProfileDataClass
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.ContactSupport.ContactSupportRequest
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.ContactSupport.ContactSupportResponce
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.DeleteAccountResponse
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.LogoutRequestDataClass
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.LogoutResponse
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.TermAndCondition.TermAndConditionRequest
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.TermAndCondition.TermAndConditionResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiServices {

    @FormUrlEncoded
    @POST("register")
    fun signUp(
        @Field("about") about: String?,
        @Field("birthday") birthday: String?,
        @Field("education") education: String?,
        @Field("email") email: String?,
        @Field("favorite_food") favoriteFood: String?,
        @Field("first_name") firstName: String?,
        @Field("gender") gender: String?,
        @Field("height") height: String?,
        @Field("last_name") lastName: String?,
        @Field("profession") profession: String?,
        @Field("profile_image_url") profileImageUrl: String,
        @Field("sign") sign: String?,
        @Field("city") city: String?,
        @Field("device_id") deviceId: String?,
        @Field("firebase_token") firebaseToken: String?,
        @Field("device_type") deviceType: String?,
        @Field("latitude") latitude: Double?,
        @Field("longitude") longitude: Double?,
        @Field("state") state: String?,
        @Field("password") password: String?,
        @Field("age") age: String?
    ): Call<SignupResponse>
    @Multipart
    @POST("image")
    fun uploadImage(@Part files: MultipartBody.Part): Call<UploadImageResponse>

    @POST("signin")
    fun signIn(@Body request: SignInDataClass): Call<SignInResponse>

    @POST("forgot-password")
    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<ForgotPasswordResponse>

    @POST("verify-otp")
    fun verifyOTP(@Body request: VerifyOTPRequest): Call<VerifyOTPResponse>

    @POST("confirm-password")
    fun changePassword(@Body request: ChangePasswordRequest): Call<ChangePasswordResponse>

    @POST("logout")
    fun logout(@Header("Authorization") token: String?,@Body request: LogoutRequestDataClass): Call<LogoutResponse>

    @GET("delete-account")
    fun deleteAccount(@Header("Authorization") token:String?):Call<DeleteAccountResponse>

    @POST("terms-conditions")
    fun termAndCondition(@Body request: TermAndConditionRequest): Call<TermAndConditionResponse>

    @GET("profile")
    fun getProfile(@Header("Authorization") token: String?): Call<ProfileDataClass>

    @GET("offer-details")
    fun getOfferDetails(@Header("Authorization") token: String?): Call<OffersDataClass>
    @POST("create-offers")
    fun createOffer(@Header("Authorization") token: String?,@Body request: CreateOfferRequest): Call<CreateOfferResponse>

    @POST("delete-offer")
    fun deleteOffer(@Header("Authorization") token: String?,@Body request: DeleteOfferDataClass): Call<DeleteOfferResponseDataClass>
    @POST("edit-offer")
    fun editOffer(@Header("Authorization") token: String?,@Body request: EditOffer): Call<EditOfferResponse>
    @POST("home")
    fun getHomeData(
        @Header("Authorization") token: String?,
        @Body request: HomeRequest
    ): Call<HomeDataClass>

    @GET("maps/api/place/nearbysearch/json?location=30.7333,76.7794&radius=100000&type=Allrestaurant&keyword=dinner&key=AIzaSyAysVHVKFJTJe-LKZtH-PVlwX53XH-wuIw")
    fun getGoogleRestaurant(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("keyword") keyword: String,
        @Query("key") apiKey: String
    ): Call<RestaurantDataClass>

    @POST("edit-profile")
    fun editProfile(  @Header("Authorization") token: String?,
                      @Body request: EditProfileRequest): Call<EditProfileResponse>

    @POST("contact-support")
    fun contactSupport(@Body request: ContactSupportRequest): Call<ContactSupportResponce>
}
