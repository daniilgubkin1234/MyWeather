package com.example.myweather.ui.week

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweather.R
import com.example.myweather.databinding.FragmentWeekBinding
import com.example.myweather.models.ForecastItem
import com.example.myweather.ui.home.WeatherViewModel
import com.example.myweather.ui.network.WeatherApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeekFragment : Fragment() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherApiService = retrofit.create(WeatherApiService::class.java)
    private lateinit var binding: FragmentWeekBinding
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_week, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        binding.viewModel = weatherViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.weekWeatherRecyclerView.layoutManager = LinearLayoutManager(context)

        weatherViewModel.weeklyWeatherData.observe(viewLifecycleOwner, { forecastItems ->
            if (forecastItems != null) {
                Log.d("WeekFragment", "5-day forecast data: $forecastItems")
                binding.weekWeatherRecyclerView.adapter = WeekWeatherAdapter(forecastItems)
            } else {
                Log.d("WeekFragment", "5-day forecast data is null")
            }
        })

        val cityName = "Архангельск" // Замените на нужное имя города для получения прогноза
        getFiveDayForecast(cityName)
    }

    private fun getFiveDayForecast(cityName: String) {
        lifecycleScope.launch {
            val response = weatherApiService.getFiveDayForecast(cityName, "58f6f2701b3c01446fd3b0a8293a9e53")
            if (response.isSuccessful) {
                val forecastResponse = response.body()
                if (forecastResponse != null) {
                    weatherViewModel.setWeeklyWeatherData(forecastResponse.list)
                    Log.d("WeekFragment", "5-day forecast data loaded: ${forecastResponse.list}")
                } else {
                    Log.d("WeekFragment", "Response body is null")
                }
            } else {
                Log.d("WeekFragment", "Failed to load 5-day forecast data: ${response.errorBody()?.string()}")
            }
        }
    }
}
