package com.nilevia.data.di

import com.nilevia.data.local.NileviaDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun localModule() = module {
    single(named("NILEVIA_DATABASE")) { NileviaDatabase.getInstance(androidContext()) }
    single(named("CITY_DAO")) {
        get<NileviaDatabase>(named("NILEVIA_DATABASE")).cityDao()
    }
}