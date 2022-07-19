package com.example.hijob.jobOffer

import com.example.hijob.entities.Job

interface OnJobListener {
    fun onJob(job: Job)
}