package com.interview.safeboda.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareFlightViewModel : ViewModel() {

    private val departureAirport = MutableLiveData<String>()
    private val arivalAirport = MutableLiveData<String>()


    val departureLiveData: LiveData<String>
        get() = departureAirport



    val arrivalLiveData:LiveData<String>
        get() = arivalAirport


    fun setFlight(departure: String) {
        departureAirport.value = departure
    }


    fun setarrivalAirpot(arrival:String){
        arivalAirport.value = arrival
    }




}
