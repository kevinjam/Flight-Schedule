package com.interview.safeboda.modelLayer

import com.interview.safeboda.modelLayer.model.airport.Payload
import com.interview.safeboda.modelLayer.model.airport.RequestToken
import com.interview.safeboda.modelLayer.model.schedule.ScheduleModel
import com.interview.safeboda.network.NetworkLayer
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Single

class ModelLayer {

    companion object {
        val shared = ModelLayer()
    }

    val tokenResponse = BehaviorRelay.create<RequestToken>()
    val schedules = BehaviorRelay.create<Payload>()
    private val networkLayer = NetworkLayer.instance



    //region GetToken Layer
    fun getToken(){
        return networkLayer.getToken({
            token->
            this.tokenResponse.accept(token)
        },{
                errorMessage->
            notifyError(errorMessage )
        })
    }
    //endregion


    private fun notifyError(errorMessage:String){
        println("❗️On Error ${error(errorMessage)})")
    }


    fun getTokenRx():Single<RequestToken>{
        return networkLayer.getTokenRx()
    }

    fun getSchedule():Single<Payload>{
        return networkLayer.schedules()
    }

    //region flight
    fun flights(origin: String, destination: String, fromDateTime: String):Single<ScheduleModel>{
        return  networkLayer.flight(origin, destination, fromDateTime)
    }
    //endregion

}