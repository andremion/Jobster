package io.github.andremion.interprep

import android.app.Application
import io.github.andremion.interprep.di.MainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApp)
            modules(MainModule.modules)
        }
    }
}