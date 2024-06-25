package com.example.myweather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.data.WeatherResponse
import com.example.myweather.models.ForecastItem
import com.example.myweather.models.WeekWeatherModel

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData

    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String> get() = _cityName
    private val _weeklyWeatherData = MutableLiveData<List<ForecastItem>>()
    val weeklyWeatherData: LiveData<List<ForecastItem>> = _weeklyWeatherData

    fun setWeatherData(weatherResponse: WeatherResponse) {
        _weatherData.value = weatherResponse
    }

    fun setCityName(city: String) {
        _cityName.value = city
    }

    fun setWeeklyWeatherData(data: List<ForecastItem>) {
        _weeklyWeatherData.value = data
    }
}
