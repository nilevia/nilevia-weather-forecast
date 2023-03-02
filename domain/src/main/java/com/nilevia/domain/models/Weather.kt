package com.nilevia.domain.models


data class Weather(
    // not save city because it inconsistent of name
    val date: String,
    //val hour: String,
    val status: String,
    val temperatureMin: String,
    val temperatureMax: String,
    val humidity: String,
    val wind: String
)

