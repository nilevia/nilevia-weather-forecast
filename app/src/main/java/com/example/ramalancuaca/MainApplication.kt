package com.example.ramalancuaca

import android.app.Application
import com.example.ramalancuaca.di.getAppComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.java.KoinAndroidApplication
import org.koin.core.logger.Level
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        setupKoin()
        super.onCreate()
    }

    private fun setupKoin(){
        val koinApp = KoinAndroidApplication.create(this, Level.NONE)
            .modules(getAppComponent())
            .androidContext(this)
        startKoin(koinApp)
    }

}