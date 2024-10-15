package com.example.ssd_e_commerce.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREF_NAME = "AppPrefs"
        const val USER_TOKEN = "user_token"
        const val USER_NAME = "user_name"
    }

    fun saveAuthToken(token: String, name: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putString(USER_NAME, name)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchUserName(): String? {
        return prefs.getString(USER_NAME, null)
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}