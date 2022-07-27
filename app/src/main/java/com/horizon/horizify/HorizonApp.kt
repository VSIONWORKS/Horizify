package com.horizon.horizify

import android.app.Application
import com.horizon.horizify.modules.appModules
import org.koin.core.context.startKoin

class HorizonApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            modules(appModules)
        }
    }
}