package com.nilevia.domain.usecases

import com.nilevia.domain.models.City
import com.nilevia.domain.models.Weather
import com.nilevia.domain.repositories.WeatherForecastRepository
import com.nilevia.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

// usecase use if we want to mondify or do some business logic
// because this app is simple, let it as bridge only
class WeatherForecastUsecase(
    private val repo: WeatherForecastRepository
) {

    fun getFavoriteCity(): Flow<Resource<List<City>?>> = repo.getFavoriteCity()

    fun searchCity(query: String): Flow<Resource<List<City>>> = repo.searchCity(query)

    fun saveFavoriteCity(selectedCity: City, id: String) : Flow<Resource<Any>> {
        val formattedCity = City(
            id = id,
            fullName = selectedCity.fullName,
            lat = selectedCity.lat,
            lon = selectedCity.lon
        )
        return repo.saveFavoriteCity(formattedCity)
    }

    fun deleteFavoriteCity(selectedCity: City) : Flow<Resource<Any>> {
        return repo.deleteFavoriteCity(selectedCity.id)
    }

    fun getWeatherForecast(selectedCity: City): Flow<Resource<List<Weather>>> {
        return repo.getWeatherForecast(selectedCity.lat, selectedCity.lon)
    }


}