package com.example.hijob

import java.util.*

data class Job(var active: Boolean  ?= false, var category: String ?= null, var company: String ?= null, var date: Date, var description: String ?= null, var location: String ?= null, var position: String ?= null)
