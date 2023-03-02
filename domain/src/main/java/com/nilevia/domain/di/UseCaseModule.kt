package com.nilevia.domain.di

import com.nilevia.domain.usecases.WeatherForecastUsecase
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun useCaseModule() = module {

    factory(named("GET_WEATHER_FORECAST_USECASE")) { WeatherForecastUsecase(get(named("REMOTE_REPO"))) }
}