package com.interview.safeboda.network.endpoint

import com.interview.safeboda.modelLayer.model.airport.Payload
import com.interview.safeboda.modelLayer.model.airport.RequestToken
import com.interview.safeboda.modelLayer.model.schedule.ScheduleModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface Lufthansa {

    @Headers(
        "Accept: application/json"
    )
    @GET("/v1/references/airports?limit=20&offset=0&LHoperated=0&lang=en")
    fun airports(): Observable<Payload>


    @Headers(
        "Accept: application/json"
    )
    @GET("/v1/references/airports?limit=20&offset=0&LHoperated=0&lang=en")
    fun airport(): Single<Payload>


    @GET("/v1/operations/schedules/{origin}/{destination}/{fromDateTime}?limit=10&offset=0&directFlights=0")
    fun flightScheduleRx(
        @Path(value = "origin") origin: String,
        @Path("destination") destination: String,
        @Path("fromDateTime") fromDateTime: String
    ): Single<ScheduleModel>




    @GET("/v1/operations/schedules/{origin}/{destination}/{fromDateTime}?limit=10&offset=0&directFlights=0")
    fun flightSchedule(
        @Path(value = "origin") origin: String,
        @Path("destination") destination: String,
        @Path("fromDateTime") fromDateTime: String
    ): Observable<ScheduleModel>

    @FormUrlEncoded
    @POST("v1/oauth/token")
    fun getToken(
        @Field("client_id") client_id:String,
        @Field("client_secret") client_secret:String,
        @Field("grant_type") grant_type:String
    ):Call<RequestToken>



    @FormUrlEncoded
    @POST("v1/oauth/token")
    fun getTokenRx(
        @Field("client_id") client_id:String,
        @Field("client_secret") client_secret:String,
        @Field("grant_type") grant_type:String
    ):Single<RequestToken>

}

//references/airports?limit=20&offset=0&LHoperated=0&lang=en