package com.interview.safeboda.activities.airport.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interview.safeboda.modelLayer.model.airport.Airport

class AirportViewModel:ViewModel(){

    private val departureAirport = MutableLiveData<Airport>()
    private val arivalAirport = MutableLiveData<Airport>()


    val departureLiveData: LiveData<Airport>
        get() = departureAirport



    val arrivalLiveData:LiveData<Airport>
        get() = arivalAirport


    fun setdeparture(departure: Airport) {
        departureAirport.value = departure
    }


    fun setarrivalAirpot(arrival:Airport){
        arivalAirport.value = arrival
    }


}