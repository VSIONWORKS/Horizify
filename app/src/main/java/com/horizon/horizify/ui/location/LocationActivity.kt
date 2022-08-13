package com.horizon.horizify.ui.location

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.horizon.horizify.R
import com.horizon.horizify.databinding.ActivityLocationBinding
import com.horizon.horizify.ui.location.model.LocationModel
import com.horizon.horizify.utils.Constants.CUBAO_LATITUDE
import com.horizon.horizify.utils.Constants.CUBAO_LOCATION_NAME
import com.horizon.horizify.utils.Constants.CUBAO_LONGITUDE
import com.horizon.horizify.utils.Constants.MATIENZA_LATITUDE
import com.horizon.horizify.utils.Constants.MATIENZA_LOCATION_NAME
import com.horizon.horizify.utils.Constants.MATIENZA_LONGITUDE

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityLocationBinding

    private lateinit var map: GoogleMap

    private val zoomLevel = 15.8f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setUpButtons()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setLocation(
            LocationModel(
                locationName = CUBAO_LOCATION_NAME,
                latitude = CUBAO_LATITUDE,
                longitude = CUBAO_LONGITUDE
            )
        )
    }

    private fun setUpButtons() {
        with(binding) {
            btnCubao.setOnClickListener { setLocationCubao() }
            layoutCubao.setOnClickListener { setLocationCubao() }
            btnMatienza.setOnClickListener { setLocationMatienza() }
            layoutMatienza.setOnClickListener { setLocationMatienza() }
        }
    }

    private fun setLocationCubao() {
        setLocation(
            LocationModel(
                locationName = CUBAO_LOCATION_NAME,
                latitude = CUBAO_LATITUDE,
                longitude = CUBAO_LONGITUDE
            )
        )
    }

    private fun setLocationMatienza() {
        setLocation(
            LocationModel(
                locationName = MATIENZA_LOCATION_NAME,
                latitude = MATIENZA_LATITUDE,
                longitude = MATIENZA_LONGITUDE
            )
        )
    }

    private fun setLocation(location: LocationModel) {
        with(location) {
            val mark = LatLng(latitude, longitude)
            map.addMarker(MarkerOptions().position(mark).title(locationName))?.showInfoWindow()
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mark, zoomLevel))
        }
    }
}