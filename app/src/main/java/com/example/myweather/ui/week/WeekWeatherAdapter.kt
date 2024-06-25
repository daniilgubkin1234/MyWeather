package com.example.myweather.ui.week

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.R
import com.example.myweather.models.ForecastItem
import java.text.SimpleDateFormat
import java.util.*

class WeekWeatherAdapter(private val forecastList: List<ForecastItem>) :
    RecyclerView.Adapter<WeekWeatherAdapter.WeekWeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekWeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_week_weather, parent, false)
        return WeekWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeekWeatherViewHolder, position: Int) {
        val item = forecastList[position]
        holder.dayOfWeek.text = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date(item.dt * 1000))
        holder.date.text = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()).format(Date(item.dt * 1000))
        holder.temperature.text = "${item.main.temp.toInt()}°C"
        holder.weatherIcon.setImageResource(getWeatherIcon(item.weather[0].icon))
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    private fun getWeatherIcon(icon: String): Int {
        return when (icon) {
            "01d" -> R.drawable.sunny
            "01n" -> R.drawable.clear_night
            "02d", "02n" -> R.drawable.partly_cloudy
            "03d", "03n", "04d", "04n" -> R.drawable.cloudy
            "09d", "09n", "10d", "10n" -> R.drawable.rainy
            "11d", "11n" -> R.drawable.thunderstorm
            "13d", "13n" -> R.drawable.snow
            "50d", "50n" -> R.drawable.mist
            else -> R.drawable.default_weather
        }
    }

    class WeekWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfWeek: TextView = itemView.findViewById(R.id.dayOfWeek)
        val date: TextView = itemView.findViewById(R.id.date)
        val temperature: TextView = itemView.findViewById(R.id.temperature)
        val weatherIcon: ImageView = itemView.findViewById(R.id.weatherIcon)
    }
}
