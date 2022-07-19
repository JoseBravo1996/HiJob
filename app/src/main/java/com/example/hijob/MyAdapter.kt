package com.example.hijob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hijob.entities.Job
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyAdapter(private val jobList: ArrayList<Job>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.job_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = jobList[position]
      //  holder.company.text = currentItem.company
       // holder.position.text = currentItem.position
        //holder.date.text = getDate(currentItem.date!!).toString()


    }
    private fun getDate(time: Date): String? {
        return SimpleDateFormat("dd/MM/yyyy").format(time)
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       // val company: TextView = itemView.findViewById(R.id.company)
       // val position: TextView = itemView.findViewById(R.id.position)
        //val date: TextView = itemView.findViewById(R.id.date)
    }

}