package com.interview.safeboda.activities.schedules

import com.interview.safeboda.modelLayer.model.airport.Payload
import com.interview.safeboda.modelLayer.ModelLayer
import io.reactivex.Single

class SchedulePresenter{

    private val modelLayer = ModelLayer.shared

    fun airportRx(): Single<Payload> {
        return modelLayer.getSchedule()
    }

}