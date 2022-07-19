package com.example.hijob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hijob.entities.Job
import com.example.hijob.jobDetail.JobAux
import com.example.hijob.jobDetail.JobFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

class JobListActivity : AppCompatActivity(), JobAux{

    private val db = FirebaseFirestore.getInstance()
    private val collectionReference = db.collection("JobOffer")

    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobArrayList: ArrayList<Job>
    private  lateinit var myAdapter: MyAdapter
    private lateinit var jobSelected: Job

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_list)

        jobRecyclerView = findViewById(R.id.jobList)
        jobRecyclerView.layoutManager = LinearLayoutManager(this)
        jobRecyclerView.setHasFixedSize(true)

        jobArrayList = arrayListOf<Job>()
        myAdapter = MyAdapter(jobArrayList)

        jobRecyclerView.adapter = myAdapter

        getJobs()

        auth = Firebase.auth
    }

    private fun getJobs(){
        collectionReference.orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener {
            for (document in it){
                val doc = document.toObject(Job::class.java)
                doc.id = document.id
                jobArrayList.add(doc)

            }
            myAdapter.notifyDataSetChanged()
        }
    }


    fun onJob(job: Job){
        jobSelected = job
        val fragment = JobFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.containerMain, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun getJobSelected(): Job = jobSelected

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}