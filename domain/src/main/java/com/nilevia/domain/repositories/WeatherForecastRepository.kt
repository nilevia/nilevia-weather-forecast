package com.nilevia.domain.repositories

import com.nilevia.domain.models.City
import com.nilevia.domain.models.Weather
import com.nilevia.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherForecastRepository {

    fun searchCity(query: String): Flow<Resource<List<City>>>
    fun getFavoriteCity(): Flow<Resource<List<City>?>>
    fun saveFavoriteCity(city: City) : Flow<Resource<Any>>
    fun deleteFavoriteCity(id: String): Flow<Resource<Any>>
    fun getWeatherForecast(lat: String, lon: String): Flow<Resource<List<Weather>>>

}