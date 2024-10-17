package com.example.ssd_e_commerce.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREF_NAME = "AppPrefs"
        const val USER_TOKEN = "user_token"
        const val USER_NAME = "user_name"
        const val USER_EMAIL = "user_email"
        const val USER_ROLE = "user_role"
    }

    fun saveAuthToken(token: String, name: String, email: String, role: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putString(USER_NAME, name)
        editor.putString(USER_EMAIL, email)
        editor.putString(USER_ROLE, role)
        editor.apply()
    }

    fun fetchAuthToken(): String? = prefs.getString(USER_TOKEN, null)
    fun fetchUserName(): String? = prefs.getString(USER_NAME, null)
    fun fetchUserEmail(): String? = prefs.getString(USER_EMAIL, null)
    fun fetchUserRole(): String? = prefs.getString(USER_ROLE, null)

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}