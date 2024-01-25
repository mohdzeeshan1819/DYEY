package com.example.dyey.apiInterfaces

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

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

    fun setUserImage(userImage: String) {
        editorUser?.putString("userImage", userImage)
        editorUser?.commit()
    }

    fun getUserImage(): String? {
        return sharedPreferencesUser?.getString("userImage", "")!!
    }

    fun getIsLoggedIn(): Boolean {
        return sharedPreferencesUser?.getBoolean("isLoggedIn", false)!!
    }

    fun setIsLoggedIn(isLoggedIn: Boolean) {
        editorUser?.putBoolean("isLoggedIn", isLoggedIn)
        editorUser?.commit()
    }

//    fun getFcmToken():String{
//        // Get FCM token
//        var token =""
//        FirebaseMessaging.getInstance().token
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    token= task.result
//                    Log.d("FCM Token", "Token: $token")
//
//                    // Here you can use the FCM token as needed, e.g., sending it to your server
//                } else {
//                    Log.e("FCM Token", "Failed to get token: ${task.exception}")
//                    token=""
//                }
//            }
//
//        return token;
//    }
}