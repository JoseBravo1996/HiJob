package com.example.hijob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.hijob.databinding.ActivityRegisterBinding
import com.example.hijob.databinding.ActivityUserBinding
import com.example.hijob.jobOffer.JobOfferActivity
import com.example.hijob.sesion.UserApplication
import com.google.android.material.bottomnavigation.BottomNavigationView
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
            val intent: Intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    private fun addActionsToNav() {
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.selectedItemId = R.id.user
        navView.setOnItemSelectedListener { menuItem: MenuItem ->
            when(menuItem.itemId) {
                R.id.home -> {
                    val intent: Intent = Intent(this, JobOfferActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnItemSelectedListener true
                }

                R.id.maps -> {
                    val intent: Intent = Intent(this, MapsActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnItemSelectedListener true
                }

                R.id.user -> {
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener true
            }
        }
    }
}