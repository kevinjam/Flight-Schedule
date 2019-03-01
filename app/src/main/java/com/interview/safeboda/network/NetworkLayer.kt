package com.interview.safeboda.network

import com.interview.safeboda.modelLayer.model.airport.Payload
import com.interview.safeboda.modelLayer.model.airport.RequestToken
import com.interview.safeboda.common.Constants.Companion.CLIENT_ID
import com.interview.safeboda.common.Constants.Companion.CLIENT_SECRET
import com.interview.safeboda.common.Constants.Companion.GRANT_TYPE
import com.interview.safeboda.utils.helper.Helper
import com.interview.safeboda.common.StringLambda
import com.interview.safeboda.modelLayer.model.schedule.ScheduleModel
import com.interview.safeboda.network.endpoint.Lufthansa
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


typealias TokenLambda = (RequestToken?)->Unit


class NetworkLayer{


    companion object {
        val instance = NetworkLayer()
    }

    val apiService : Lufthansa = Helper.createService(Lufthansa::class.java)



    fun getTokenRx():Single<RequestToken>{
       return  apiService.getTokenRx(CLIENT_ID, CLIENT_SECRET, GRANT_TYPE)
    }


    fun schedules(): Single<Payload> {
        return apiService.airport()
    }

    fun flight(origin: String, destination: String, fromDateTime: String):Single<ScheduleModel>{
        return apiService.flightScheduleRx(origin, destination, fromDateTime)
    }

    fun getToken(success:TokenLambda, failure: StringLambda){

        val call = apiService.getToken(CLIENT_ID, CLIENT_SECRET, GRANT_TYPE)

        call.enqueue(object : Callback<RequestToken>{
            override fun onFailure(call: Call<RequestToken>, t: Throwable) {

                failure(t.localizedMessage)
            }

            override fun onResponse(call: Call<RequestToken>, response: Response<RequestToken>) {

              val respToken = handleResponse(response)
                success(respToken)
                println("😜 Success $respToken")
            }

        })
    }


private fun<T> handleResponse(response: Response<T>?):T?{

    val token = response!!.body()

    if (token == null){
        handleResponseError(response)
    }
    return token
}


    //region Handle Error Response
    private fun<T> handleResponseError(response: Response<T>?){
        if (response == null) return //can't do anything

        val responseBody = response.errorBody()
        if (responseBody != null){
            try {
                println("My Response Body $responseBody")
            }catch (e:IOException){
                e.printStackTrace()
            }
        }else{
            val responseNull = "responseBody == null"
            //Handler
            println(responseNull)
        }
    }


    //endregion
}