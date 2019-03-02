package com.interview.safeboda.modelLayer.model.schedule

import com.google.gson.annotations.SerializedName

data class Equipment(
    val AircraftCode: String,
    @SerializedName("OnBoardEquipment")
    val onboarding: OnBoardEquipment
)

data class OnBoardEquipment(
    @SerializedName("InflightEntertainment")
    var flightEnt: Boolean,
    @SerializedName("Compartment")
    var compartment: List<Compartment>
)


data class Compartment(
    @SerializedName("ClassCode")
    var classcode: String,
    @SerializedName("ClassDesc")
    var classdesc: String,
    @SerializedName("FlyNet")
    var flynet: Boolean,
    @SerializedName("SeatPower")
    var seatPower: Boolean,
    @SerializedName("Usb")
    var usb: Boolean,
    @SerializedName("LiveTv")
    var liveTv: Boolean)