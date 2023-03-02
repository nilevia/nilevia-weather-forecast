package com.nilevia.data.remote

import com.nilevia.data.BuildConfig
import com.nilevia.data.models.CityDTO
import com.nilevia.data.models.WeatherDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("geo/1.0/direct?appid=${BuildConfig.API_KEY}")
    suspend fun searchCity(@QueryMap queryMap : Map<String, String> ): Response<List<CityDTO>>

    @GET("data/2.5/forecast?appid=${BuildConfig.API_KEY}")
    suspend fun getWeather(@QueryMap queryMap : Map<String, String> ): Response<WeatherDTO>


}