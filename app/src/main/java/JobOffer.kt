package com.example.hijob
import com.google.firebase.firestore.PropertyName

data class JobOffer(
    @get: PropertyName("active") @set: PropertyName("active") var active: Boolean = false,
    @get: PropertyName("category") @set: PropertyName("category") var category: String = "",
    @get: PropertyName("company") @set: PropertyName("company") var company: String = "",
    @get: PropertyName("position") @set: PropertyName("position") var position: String = "",
    @get: PropertyName("description") @set: PropertyName("description") var description: String = "",
    @get: PropertyName("lat") @set: PropertyName("lat") var lat: Double = 0.0,
    @get: PropertyName("long") @set: PropertyName("long") var long: Double = 0.0,
)