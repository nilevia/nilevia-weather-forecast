package com.example.ramalancuaca.di

import com.example.ramalancuaca.BuildConfig
import com.example.ramalancuaca.ui.CityViewModel
import com.example.ramalancuaca.ui.WeatherViewModel
import com.nilevia.data.di.apiModule
import com.nilevia.data.di.localModule
import com.nilevia.data.di.networkModule
import com.nilevia.data.di.repositoryModule
import com.nilevia.domain.di.useCaseModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun getAppComponent() = listOf(
    networkModule(BuildConfig.API_BASE_URL),
    apiModule(),
    localModule(),
    repositoryModule(),
    useCaseModule(),
    module {
        viewModel{ CityViewModel(get(named("GET_WEATHER_FORECAST_USECASE"))) }
        viewModel{ WeatherViewModel(get(named("GET_WEATHER_FORECAST_USECASE"))) }
    }
)
