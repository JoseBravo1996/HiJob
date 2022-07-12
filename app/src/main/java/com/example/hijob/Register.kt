package com.example.hijob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.hijob.databinding.ActivityLoginBinding.inflate
import com.example.hijob.databinding.ActivityMainBinding.inflate
import com.example.hijob.databinding.ActivityRegisterBinding
import com.example.hijob.databinding.ActivityRegisterBinding.inflate
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailFocusListener()

        firebaseAuth = FirebaseAuth.getInstance()

        binding.haventAccount.setOnClickListener {
            goToLogin()
        }

        binding.registerBtn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            val confirmPass = binding.etRepeatPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun goToLogin(){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    private fun emailFocusListener(){
        binding.etEmail.setOnFocusChangeListener{_, focused ->
            if(!focused){
                if(Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
                    binding.registerBtn.isEnabled = true
                }else{
                    binding.registerBtn.isEnabled = false
                    binding.registerBtn.error = "Email invalido"
                }
            }
        }

    }
}