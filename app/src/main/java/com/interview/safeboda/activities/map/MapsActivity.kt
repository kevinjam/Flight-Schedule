package com.interview.safeboda.activities.map

import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.*
import com.interview.safeboda.R
import com.interview.safeboda.common.Constants.Companion.AIRPORT_ARRIVAL
import com.interview.safeboda.common.Constants.Companion.AIRPORT_ORIGINE
import com.interview.safeboda.modelLayer.model.airport.Airport
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.toolbar_map.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    private var departure_airport:Airport?= null
    private var arrival_airport:Airport?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        departure_airport= intent.getSerializableExtra(AIRPORT_ORIGINE) as Airport
        arrival_airport= intent.getSerializableExtra(AIRPORT_ARRIVAL) as Airport
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        navigation_menu.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onMapReady(myMap: GoogleMap) {
        googleMap = myMap

        val departureAirportLatitude =  departure_airport!!.position.coordinates.latitude  //57.0928 //latitude
        val departureAirportLong =departure_airport!!.position.coordinates.longitude //9.8492 //longitude

        arrival_txt.text = departureAirportLatitude.toString()


        val departureLatLng = LatLng(departureAirportLatitude, departureAirportLong)
       val deparMarker = googleMap.addMarker(MarkerOptions().position(departureLatLng).title(departure_airport!!.name.name.countryName))
        deparMarker.showInfoWindow()



        val arrivalLatLng = LatLng(arrival_airport!!.position.coordinates.latitude,arrival_airport!!.position.coordinates.latitude)
        val arrivMarket =googleMap.addMarker(MarkerOptions().position(arrivalLatLng).title(arrival_airport!!.name.name.countryName))
        arrivMarket.showInfoWindow()


        googleMap.addPolyline(
            PolylineOptions()
                .add(departureLatLng, arrivalLatLng)
                .width(4f)
                .color(Color.RED))

        googleMap.setOnMarkerClickListener (onMarkerClickedListener)


        val bounds = LatLngBounds.Builder()
            .include(departureLatLng)
            .include(arrivalLatLng)
            .build()
        val displaySize = Point()
        windowManager.defaultDisplay.getSize(displaySize)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 150, 30))




        googleMap.addCircle(
            CircleOptions()
                .center(LatLng(departureAirportLatitude, departureAirportLong))
                .radius(200.0)
                .strokeColor(Color.BLUE)
                .strokeWidth(0f)
                .fillColor(Color.BLACK)
                .fillColor(Color.parseColor("#26006ef1"))
        )

        val style = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json)

        googleMap.setMapStyle(style)



        // create marker
//        val marker = MarkerOptions().position(departureLatLng).title("Set Pickup Point")
//        // adding marker
//        googleMap.addMarker(marker)
//
//        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
//

        val cameraPosition = CameraPosition.Builder().target(
            arrivalLatLng
        ).zoom(10f).build()


        googleMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(cameraPosition)
        )

    }

    private val onMarkerClickedListener = GoogleMap.OnMarkerClickListener { marker ->
        if (marker.isInfoWindowShown) {
            marker.showInfoWindow()
        } else {
            marker.showInfoWindow()
        }
        true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
