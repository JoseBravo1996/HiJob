package com.example.hijob.jobOffer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hijob.entities.Job
import com.example.hijob.R
import com.example.hijob.databinding.JobItemBinding
import java.text.SimpleDateFormat
import java.util.*

class JobAdapter(private val jobList: MutableList<Job>, private val listener: OnJobListener) :
RecyclerView.Adapter<JobAdapter.ViewHolder>()
{
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.job_item,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = jobList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val job = jobList[position]
        holder.setListener(job)

        holder.binding.jobId.text = job.id
        holder.binding.jobCompany.text = job.company
        holder.binding.jobPosition.text = job.position
        holder.binding.jobDate.text = getDate(job.date!!).toString()
    }
    fun add(job: Job){
        jobList.add(job)
        notifyItemInserted(jobList.size - 1)
    }

    private fun getDate(time: Date): String? {
        return SimpleDateFormat("dd/MM/yyyy").format(time)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
     val binding = JobItemBinding.bind(view)
     fun setListener(job: Job){
         binding.btnTrack.setOnClickListener{
             listener.onJob(job)
         }
     }
 }
}