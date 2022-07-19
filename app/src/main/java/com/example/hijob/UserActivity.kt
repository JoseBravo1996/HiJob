package com.example.hijob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hijob.databinding.ActivityRegisterBinding
import com.example.hijob.databinding.ActivityUserBinding
import com.example.hijob.sesion.UserApplication
import com.google.firebase.auth.FirebaseAuth

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            if(UserApplication.prefs.getUser()?.isNotEmpty() == true){
                UserApplication.prefs.deleteUser()
            }
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}