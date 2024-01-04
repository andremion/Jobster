package io.github.andremion.jobster

import android.app.Application
import io.github.andremion.jobster.di.MainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApp : Application() {
    companion object {
        lateinit var INSTANCE: MainApp
            private set
    }

    override fun onCreate() {
        INSTANCE = this
        super.onCreate()

        startKoin {
            androidContext(this@MainApp)
            modules(MainModule.modules)
        }
    }
}
