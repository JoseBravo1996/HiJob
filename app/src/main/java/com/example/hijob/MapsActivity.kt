package com.example.hijob

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hijob.jobOffer.JobOfferActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener {

    private lateinit var map: GoogleMap
    var db = FirebaseFirestore.getInstance()
    private val allJobOffers = mutableListOf<JobOffer>()

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        createFragment()
        addActionsToNav()
    }

    private fun addActionsToNav() {
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.selectedItemId = R.id.maps
        navView.setOnItemSelectedListener { menuItem: MenuItem ->
            when(menuItem.itemId) {
                R.id.home -> {
                    val intent: Intent = Intent(this, JobOfferActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnItemSelectedListener true
                }

                R.id.maps -> {
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener true
            }
        }
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(gogleMap: GoogleMap) {
        map = gogleMap
        // createUNAJMaker()
        map.setOnMyLocationClickListener(this)
        // custom marker
        map.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
        enableMyLocation()
        getJobs()
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
                        .position(coordinates)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                )
                // Set place as the tag on the marker object so it can be referenced within
                // MarkerInfoWindowAdapter
                mk.tag = job
            }
        }

    }
    // Real time location
    private fun isPermissionsGranted() = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isPermissionsGranted()) {
            map.isMyLocationEnabled = true
            zoomOnMyLocation()
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
                zoomOnMyLocation()
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

    @SuppressLint("MissingPermission")
    fun zoomOnMyLocation() {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        if (myLocation == null) {
            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_COARSE
            val provider = lm.getBestProvider(criteria, true)
            myLocation = lm.getLastKnownLocation(provider!!)
        }

        if (myLocation != null) {
            val userLocation = LatLng(myLocation.latitude, myLocation.longitude)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14f), 1500, null)
        }
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