package com.nilevia.data.di

import com.google.gson.GsonBuilder
import com.nilevia.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


fun networkModule(
    baseUrl: String
) = module{

    single<Interceptor>(named("INTERCEPTOR")) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        httpLoggingInterceptor
    }

    factory(named("CLIENT")){
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(get<Interceptor>(named("INTERCEPTOR")))
            .retryOnConnectionFailure(true)
            .build()
    }

    single(named("GSON_BUILDER")){
        GsonBuilder()
            .setLenient()
            .create()
    }

    single(named("RETROFIT_BUILDER")){
        Retrofit.Builder()
            .client(get(named("CLIENT")))
            .baseUrl("$baseUrl/")
            //.addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get(named("GSON_BUILDER"))))
            .build()
    }

}