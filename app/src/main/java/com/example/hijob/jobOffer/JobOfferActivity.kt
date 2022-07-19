package com.example.hijob.jobOffer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hijob.MapsActivity
import com.example.hijob.R
import com.example.hijob.UserActivity
import com.example.hijob.databinding.ActivityJobOfferBinding
import com.example.hijob.entities.Job
import com.example.hijob.jobDetail.JobAux
import com.example.hijob.jobDetail.JobFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class JobOfferActivity : AppCompatActivity(), OnJobListener, JobAux {

    private val db = FirebaseFirestore.getInstance()
    private val collectionReference = db.collection("JobOffer")

    private lateinit var binding: ActivityJobOfferBinding
    private lateinit var adapter: JobAdapter
    private lateinit var jobSelected: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addActionsToNav()
        setupRecyclerView()
        setupFirestone()
    }

    private fun addActionsToNav() {
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.selectedItemId = R.id.home
        navView.setOnItemSelectedListener { menuItem: MenuItem ->
            when(menuItem.itemId) {
                R.id.home -> {
                    return@setOnItemSelectedListener true
                }

                R.id.maps -> {
                    val intent: Intent = Intent(this, MapsActivity::class.java)
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnItemSelectedListener true
                }

                R.id.user -> {
                    val intent: Intent = Intent(this, UserActivity::class.java)
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener true
            }
        }
    }

    private fun setupRecyclerView(){
        adapter = JobAdapter(mutableListOf(), this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@JobOfferActivity)
            adapter = this@JobOfferActivity.adapter
        }
    }

    private fun setupFirestone(){
        collectionReference.orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener {
            for (document in it){
                val doc = document.toObject(Job::class.java)
                doc.id = document.id
                adapter.add(doc)

            }
        }.addOnFailureListener{
            Toast.makeText(this, "Error al consultar los datos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onJob(job: Job) {
        jobSelected = job
        val fragment = JobFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.containerMain, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun getJobSelected(): Job = jobSelected
}