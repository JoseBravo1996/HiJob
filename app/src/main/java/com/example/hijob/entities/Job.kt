package com.example.hijob.entities

import com.google.firebase.database.Exclude
import java.util.*

data class Job(@get: Exclude var id: String? = null, var description: String? = null, var category: String? = null, var active: Boolean? = false, var date: Date? = null, var company: String? = null, var position: String? = null, var lat: Double? = null, var long: Double? = null, var photo: String? = null)