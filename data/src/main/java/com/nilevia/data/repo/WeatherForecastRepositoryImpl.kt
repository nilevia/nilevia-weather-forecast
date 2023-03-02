package com.nilevia.data.repo

import com.nilevia.data.local.dao.CityDAO
import com.nilevia.data.mappers.CityMapper.mapToCity
import com.nilevia.data.mappers.CityMapper.mapToCityDTO
import com.nilevia.data.mappers.WeatherMapper.mapToWeather
import com.nilevia.data.remote.ApiService
import com.nilevia.data.remote.utils.networkHandling
import com.nilevia.domain.models.City
import com.nilevia.domain.models.Weather
import com.nilevia.domain.repositories.WeatherForecastRepository
import com.nilevia.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.HashMap

class WeatherForecastRepositoryImpl(
    private val apiService: ApiService,
    private val cityDao: CityDAO
): WeatherForecastRepository {
    override fun searchCity(query: String): Flow<Resource<List<City>>> {
        val params = HashMap<String, String>()
        params["q"] = query
        params["limit"] = "5"
        params["lang"] = "id"
        return networkHandling(
            apiCall = {apiService.searchCity(params)},
            mapper = ::mapToCity
        )
    }

    override fun getFavoriteCity(): Flow<Resource<List<City>?>> {
        return try {
            flow {
                val cities = cityDao.getCity()
                emit(Resource(Resource.Status.SUCCESS,  cities?.let {mapToCity(it)}))
            }
        } catch (e: Exception){
            flow { emit(Resource(Resource.Status.ERROR, null)) }
        }
    }

    override fun saveFavoriteCity(city: City): Flow<Resource<Any>> {
        return try {
            flow {
                cityDao.insertCity(mapToCityDTO(city))
                emit(Resource(Resource.Status.SUCCESS, null))
            }
        } catch (e:Exception){
            flow { emit(Resource(Resource.Status.ERROR, null)) }
        }
    }

    override fun deleteFavoriteCity(id: String): Flow<Resource<Any>> {
        return try {
            flow {
               cityDao.deleteCity(id)
                emit(Resource(Resource.Status.SUCCESS, null))
            }
        } catch (e:Exception){
            flow { emit(Resource(Resource.Status.ERROR, null)) }
        }
    }

    override fun getWeatherForecast(lat: String, lon: String): Flow<Resource<List<Weather>>>{
        val params = HashMap<String, String>()
        params["lat"] = lat
        params["lon"] = lon
        params["units"] = "metric"
        params["lang"] = "id"
       return networkHandling(
           apiCall = { apiService.getWeather(params)},
           mapper = ::mapToWeather
       )
    }
}
