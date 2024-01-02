package io.github.andremion.jobster.data.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.andremion.jobster.data.local.db.DatabaseFileName
import io.github.andremion.jobster.data.local.db.DestructiveMigrationSchema
import io.github.andremion.jobster.data.remote.WebScrapper
import io.github.andremion.jobster.data.remote.WebScrapperImpl
import io.github.andremion.jobster.data.remote.api.GeminiApi
import io.github.andremion.jobster.data.remote.api.GeminiApiImpl
import org.koin.dsl.module

internal actual object InternalDataModule {
    actual val module = module {
        factory<GeminiApi> { GeminiApiImpl() }
        factory<WebScrapper> { WebScrapperImpl() }
        factory<SqlDriver> {
            AndroidSqliteDriver(
                schema = DestructiveMigrationSchema,
                context = get(),
                name = DatabaseFileName
            )
        }
    }
}
