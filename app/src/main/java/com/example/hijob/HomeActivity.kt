package com.example.hijob

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hijob.entities.Job
import com.google.firebase.firestore.FirebaseFirestore


class HomeActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance()
    private lateinit var JobReciclerView: RecyclerView
    private lateinit var jobArrayList: ArrayList<Job>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getJobs()
    }

    private fun getJobs(){

        db.collection("JobOffer")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        Log.d(ContentValues.TAG,"SuperUsuario" + document.id + " => " + document.data)
                        Log.d(TAG, document.id + " => " + document.data)
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }


}