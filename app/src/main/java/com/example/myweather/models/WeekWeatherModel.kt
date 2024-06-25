// WeekWeatherModel.kt
package com.example.myweather.models

data class WeekWeatherModel(
    val cityName: String,
    val items: List<WeekWeatherItemModel>
)
