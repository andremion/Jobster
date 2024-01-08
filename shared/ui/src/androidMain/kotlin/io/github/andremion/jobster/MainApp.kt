package io.github.andremion.jobster

import android.app.Application
import io.github.andremion.jobster.di.initDI
import org.koin.android.ext.koin.androidContext

class MainApp : Application() {
    companion object {
        lateinit var INSTANCE: MainApp
            private set
    }

    override fun onCreate() {
        INSTANCE = this
        super.onCreate()

        initDI().androidContext(this)
    }
}
