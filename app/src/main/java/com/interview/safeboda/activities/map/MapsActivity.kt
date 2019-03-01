package com.interview.safeboda.activities.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.interview.safeboda.R

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val departureAirport =57.0928 //latitude
        val arrivalAirport =9.8492 //logitude
        val departureLatLng = LatLng(departureAirport, arrivalAirport)
        googleMap.addMarker(MarkerOptions().position(departureLatLng).title("Kampala"))



        val arrivalLatLng = LatLng(52.56027778, 3.29555556)
        googleMap.addMarker(MarkerOptions().position(arrivalLatLng).title("Rwanda"))

        googleMap.addPolyline(
            PolylineOptions()
                .add(departureLatLng, arrivalLatLng)
                .width(5f)
                .color(R.color.colorPrimary))


        val latLngBounds = LatLngBounds.Builder()
            .include(departureLatLng)
            .include(arrivalLatLng)
            .build()

        val zoomPadding = 200
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, zoomPadding))


        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
