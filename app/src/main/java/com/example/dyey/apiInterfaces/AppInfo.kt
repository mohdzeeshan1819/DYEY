package com.example.dyey.apiInterfaces

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.dyey.apiInterfaces.AppInfo.sharedPreferencesUser
import kotlinx.coroutines.withContext

object AppInfo {
    private var sharedPreferencesUser: SharedPreferences? = null
    private var editorUser: SharedPreferences.Editor? = null


    fun setContext(context: Context) {
        if (sharedPreferencesUser == null) {
            sharedPreferencesUser =
                context.getSharedPreferences("AppInfoUser", Context.MODE_PRIVATE);
            editorUser = sharedPreferencesUser!!.edit();
        }
    }
    fun setUserDeviceId(deviceId: String) {
        editorUser?.putString("deviceId", deviceId)
        editorUser?.commit()
    }
    fun getUserDeviceId(): String? {
        return sharedPreferencesUser?.getString("deviceId", null)
    }


    fun setUserImage(userImage: String) {
        editorUser?.putString("userImage", userImage)
        editorUser?.commit()
    }

    fun getUserImage(): String? {
        return sharedPreferencesUser?.getString("userImage", "")!!
    }
    fun setFirstName(firstName: String) {
        editorUser?.putString("FirstName", firstName)
        editorUser?.commit()
    }

    fun setLastName(lastName: String) {
        editorUser?.putString("LastName", lastName)
        editorUser?.commit()
    }

    fun setFavoriteFood(favoriteFood: String) {
        editorUser?.putString("FavoriteFood", favoriteFood)
        editorUser?.commit()
    }

    fun setEducation(education: String) {
        editorUser?.putString("Education", education)
        editorUser?.commit()
    }

    fun setGender(gender: String) {
        editorUser?.putString("Gender", gender)
        editorUser?.commit()
    }

    fun setDateOfBirth(dob: String) {
        editorUser?.putString("DOB", dob)
        editorUser?.commit()
    }

    fun setHeight(height: String) {
        editorUser?.putString("Height", height)
        editorUser?.commit()
    }

    fun setProfession(profession: String) {
        editorUser?.putString("Profession", profession)
        editorUser?.commit()
    }

    fun setEmail(email: String) {
        editorUser?.putString("Email", email)
        editorUser?.commit()
    }

    fun setPassword(password: String) {
        editorUser?.putString("Password", password)
        editorUser?.commit()
    }

    fun setAbout(about: String) {
        editorUser?.putString("About", about)
        editorUser?.commit()
    }

    fun getFirstName(): String? {
        return sharedPreferencesUser?.getString("FirstName", null)
    }

    fun getLastName(): String? {
        return sharedPreferencesUser?.getString("LastName", null)
    }

    fun getFavoriteFood(): String? {
        return sharedPreferencesUser?.getString("FavoriteFood", null)
    }

    fun getEducation(): String? {
        return sharedPreferencesUser?.getString("Education", null)
    }

    fun getGender(): String? {
        return sharedPreferencesUser?.getString("Gender", null)
    }

    fun getDateOfBirth(): String? {
        return sharedPreferencesUser?.getString("DOB", null)
    }

    fun getHeight(): String? {
        return sharedPreferencesUser?.getString("Height", null)
    }

    fun getProfession(): String? {
        return sharedPreferencesUser?.getString("Profession", null)
    }

    fun getEmail(): String? {
        return sharedPreferencesUser?.getString("Email", null)
    }

    fun getPassword(): String? {
        return sharedPreferencesUser?.getString("Password", null)
    }

    fun getAbout(): String? {
        return sharedPreferencesUser?.getString("About", null)
    }


    fun getIsLoggedIn(): Boolean {
        return sharedPreferencesUser?.getBoolean("isLoggedIn", false)!!
    }

    fun setIsLoggedIn(isLoggedIn: Boolean) {
        editorUser?.putBoolean("isLoggedIn", isLoggedIn)
        editorUser?.commit()
    }

    fun setToken(token: String) {
        editorUser?.putString("token", token)
        editorUser?.commit()
    }

    fun getToken(): String {
        return sharedPreferencesUser?.getString("token", "")!!
    }

    fun setFcmToken(fcmToken: String) {
        editorUser?.putString("fcmToken", fcmToken)
        editorUser?.commit()
    }

    fun getFcmToken(): String {
        return sharedPreferencesUser?.getString("fcmToken", "")!!
    }
}