package com.nilevia.data.di

import com.nilevia.data.remote.ApiService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

fun apiModule() = module {
    factory(named("API_SERVICE")){ get<Retrofit>(named("RETROFIT_BUILDER")).create(ApiService::class.java) }
}