package com.interview.safeboda.modelLayer.model.schedule

data class Arrival(
    val AirportCode: String,
    val ScheduledTimeLocal: ScheduledTimeLocal,
    val Terminal: Terminal
)