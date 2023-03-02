package com.nilevia.data.di

import com.nilevia.data.repo.WeatherForecastRepositoryImpl
import com.nilevia.domain.repositories.WeatherForecastRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun repositoryModule() = module {

    factory(named("REMOTE_REPO")) {
        WeatherForecastRepositoryImpl(
            get(named("API_SERVICE")),
            get(named("CITY_DAO"))
        ) as WeatherForecastRepository
    }
}