package com.nilevia.data.mappers

import com.nilevia.data.models.CityDTO
import com.nilevia.data.models.WeatherDTO
import com.nilevia.domain.models.City
import com.nilevia.domain.models.Weather
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

object WeatherMapper {
    fun mapToWeather(serverData: WeatherDTO): List<Weather> {

        val listWeather = mutableListOf<Weather>()

        serverData.list.forEach { list ->
            val weather = Weather(
                date = formatTimestampToDayName(list.dt?: 0L),
                status = list.weather[0].description.toString(), // makesure always at least 1 index
                temperatureMax = list.main?.tempMax.toString(),
                temperatureMin = list.main?.tempMin.toString(),
                humidity = list.main?.humidity.toString(),
                wind = list.wind?.speed.toString()

            )
            listWeather.add(weather)
        }
        return listWeather

    }
}

// could be move to utils but let it here for now
fun formatTimestampToDayName(timestamp: Long): String {
    val date = Date(timestamp*1000)
    val dayNameFormatter = SimpleDateFormat("EEEE, dd MMMM yyyy : hh:mm", Locale("id", "ID"))
    return dayNameFormatter.format(date)
}