package com.example.hijob.sesion

import android.content.Context
import com.google.firebase.auth.FirebaseUser

class Prefs(val context:Context) {
    var SHARED_NAME = "UserDb"
    val SHARED_USER_EMAIL = "user"
    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveUser(user: FirebaseUser){
        storage.edit().putString(SHARED_USER_EMAIL, user.email).apply()
    }

    fun getUser(): String? {
        return storage.getString(SHARED_USER_EMAIL, "")
    }

}