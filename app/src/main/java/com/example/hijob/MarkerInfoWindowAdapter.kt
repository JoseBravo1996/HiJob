package com.example.hijob

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerInfoWindowAdapter(
    private val context: Context
) : GoogleMap.InfoWindowAdapter {
    @SuppressLint("InflateParams")
    override fun getInfoContents(marker: Marker?): View? {
        // 1. Get tag
        val jobOffer = marker?.tag as? JobOffer ?: return null

        // 2. Inflate view and set title, address, and rating
        val view = LayoutInflater.from(context).inflate(
            R.layout.marker_info_contents, null
        )
        view.findViewById<TextView>(
            R.id.text_view_title
        ).text = jobOffer.position
        view.findViewById<TextView>(
            R.id.text_view_category
        ).text = jobOffer.category
        view.findViewById<TextView>(
            R.id.text_view_company
        ).text = jobOffer.company
        view.findViewById<TextView>(
            R.id.text_view_description
        ).text = jobOffer.description

        return view
    }

    override fun getInfoWindow(marker: Marker?): View? {
        // Return null to indicate that the
        // default window (white bubble) should be used
        return null
    }
}