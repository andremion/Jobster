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

package io.github.andremion.jobster.data.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.andremion.jobster.data.local.db.DatabaseFileName
import io.github.andremion.jobster.data.local.db.DestructiveMigrationSchema
import io.github.andremion.jobster.data.remote.api.GeminiApi
import io.github.andremion.jobster.data.remote.api.GeminiApiImpl
import org.koin.dsl.module

internal actual object InternalDataModule {
    actual val module = module {
        factory<GeminiApi> { GeminiApiImpl() }
        factory<SqlDriver> {
            AndroidSqliteDriver(
                schema = DestructiveMigrationSchema,
                context = get(),
                name = DatabaseFileName
            )
        }
    }
}
