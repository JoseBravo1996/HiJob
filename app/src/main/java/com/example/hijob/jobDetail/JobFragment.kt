package com.example.hijob.jobDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.hijob.entities.Job
import com.example.hijob.databinding.JobDetailBinding

class JobFragment : Fragment() {

    private var binding: JobDetailBinding? = null

    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = JobDetailBinding.inflate(inflater, container, false)
        binding?.let {
            return it.root
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getJob()
    }

    private fun getJob(){
        job = (activity as? JobAux)?.getJobSelected()
        job?.let{
            updateUI(it)
        }
    }

    private fun updateUI(job: Job){
        binding?.let {
            it.heading.text = job.position
            it.positionT.text = job.description
            Glide.with(it.profileImage).load(job.photo).into(it.profileImage)
        }
    }
}