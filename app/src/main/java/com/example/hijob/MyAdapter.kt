package com.example.hijob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val jobList: ArrayList<Job>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.job_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = jobList[position]
      //  currentItem.category.text = currentItem.category
      //  currentItem.company.text = currentItem.company
      //  currentItem.description.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       // val category: TextView = itemView.findViewById()
       // val company: TextView = itemView.findViewById()
       // val description: TextView = itemView.findViewById()
    }

}