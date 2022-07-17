package com.example.hijob

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.PropertyName
import java.util.*
import kotlin.random.Random.Default.nextInt

// jobs
data class JobOffer(
    @get: PropertyName("active") @set: PropertyName("active") var active: Boolean = false,
    @get: PropertyName("category") @set: PropertyName("category") var category: String = "",
    @get: PropertyName("company") @set: PropertyName("company") var company: String = "",
    @get: PropertyName("position") @set: PropertyName("position") var position: String = "",
    @get: PropertyName("lat") @set: PropertyName("lat") var lat: Double = 0.0,
    @get: PropertyName("long") @set: PropertyName("long") var long: Double = 0.0,
)

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener {

    private lateinit var map: GoogleMap
    var db = FirebaseFirestore.getInstance()
    val allJobOffers = mutableListOf<JobOffer>()

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        createFragment()
        getJobs()
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(gogleMap: GoogleMap) {
        map = gogleMap
        // createUNAJMaker()
        map.setOnMyLocationClickListener(this)
        enableMyLocation()
    }

    private fun createUNAJMaker() {
        val coordinates = LatLng(-34.77466314405586, -58.267594112801284)
        val marker = MarkerOptions().position(coordinates).title("UNAJ")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 15f),
            4000,
            null
        )
    }

    private fun createJobOfferMakers() {
        allJobOffers.forEach { job ->
            if(job.active) {
                val coordinates = LatLng(job.lat, job.long)
                val mk = map.addMarker(
                    MarkerOptions()
                        .title("${job.position} (${job.category})")
                        .snippet("en ${job.company}")
                        .position(coordinates)
                )
            }
        }

    }
    // Real time location
    private fun isPermissionsGranted() = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isPermissionsGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos ", Toast.LENGTH_SHORT).show()
        } else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    // Check if user disables permission on background
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if(!isPermissionsGranted()){
            map.isMyLocationEnabled = false
            Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Estas en ${p0.latitude}, ${p0.longitude}", Toast.LENGTH_SHORT).show()
    }

    private fun getJobs(){
        db.collection("JobOffer")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val job = document.toObject(JobOffer::class.java)
                    allJobOffers.add(job)
                }
                createJobOfferMakers()
            }
    }
}