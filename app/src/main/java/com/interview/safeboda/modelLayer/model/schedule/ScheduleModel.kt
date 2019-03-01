package com.interview.safeboda.modelLayer.model.schedule

import com.interview.safeboda.utils.helper.Helper

data class ScheduleModel(
    val ScheduleResource: ScheduleResource,


    val Error:String =""
){
    override fun toString(): String {
        return Helper.toStringGson().toJson(this)
    }
}