package com.interview.safeboda.activities.airport

import com.interview.safeboda.modelLayer.ModelLayer
import com.interview.safeboda.modelLayer.model.schedule.ScheduleModel
import io.reactivex.Single

class FlightPresenter {

    private val modelLayer = ModelLayer.shared

    fun getflight(origin: String, destination: String, fromDateTime: String): Single<ScheduleModel> {
        return modelLayer.flights(origin, destination, fromDateTime)
    }
}