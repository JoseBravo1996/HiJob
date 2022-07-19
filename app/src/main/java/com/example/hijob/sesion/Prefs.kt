package com.example.hijob.sesion

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
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

    fun deleteUser() {
        val editor = storage.edit()
        editor.remove(SHARED_NAME)
        editor.remove(SHARED_USER_EMAIL)
        editor.apply()
    }

 //   private fun showAlert(message: String){
   //     val builder = AlertDialog.Builder(this)
     //   builder.setTitle("My Preferencia")
       // builder.setMessage(message)
     //   val dialog = builder.create()
      //  dialog.show()
    //}



}