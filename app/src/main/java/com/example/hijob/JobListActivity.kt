package com.example.hijob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hijob.entities.Job
import com.example.hijob.jobDetail.JobAux
import com.example.hijob.jobDetail.JobFragment
import com.google.firebase.firestore.*

class JobListActivity : AppCompatActivity(), JobAux{

    private val db = FirebaseFirestore.getInstance()
    private val collectionReference = db.collection("JobOffer")

    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobArrayList: ArrayList<Job>
    private  lateinit var myAdapter: MyAdapter
    private lateinit var jobSelected: Job

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
}