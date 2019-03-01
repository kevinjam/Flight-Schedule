package com.interview.safeboda.modelLayer.model.schedule

import com.google.gson.annotations.SerializedName
import com.interview.safeboda.utils.helper.Helper


data class Schedule(
    @SerializedName("TotalJourney")
    val total: TotalJourney,
    @SerializedName("Flight")
    val flight:List<Flight>




){

    override fun toString(): String {
        return Helper.toStringGson().toJson(this)
    }


}

