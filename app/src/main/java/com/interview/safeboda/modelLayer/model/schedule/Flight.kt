package com.interview.safeboda.modelLayer.model.schedule

data class Flight(
    val Arrival: Arrival,
    val Departure: Departure,
    val Details: Details,
    val Equipment: Equipment,
    val MarketingCarrier: MarketingCarrier,
    val OperatingCarrier: OperatingCarrier
)