/*
 *    Copyright 2024. André Luiz Oliveira Rêgo
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
