package com.example.hijob.sesion

import android.content.Context
import com.google.firebase.auth.FirebaseUser

class Prefs(val context:Context) {
    var SHARED_NAME = "UserDb"
    val SHARED_USER_EMAIL = "user"
    val storage = context.getSharedPreferences(SHARED_NAME, 0)



}