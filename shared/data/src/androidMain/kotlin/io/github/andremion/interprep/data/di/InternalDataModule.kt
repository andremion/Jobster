package io.github.andremion.interprep.data.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.andremion.interprep.data.local.db.DestructiveMigrationSchema
import io.github.andremion.interprep.data.remote.WebScrapper
import io.github.andremion.interprep.data.remote.WebScrapperImpl
import io.github.andremion.interprep.data.remote.api.GeminiApi
import io.github.andremion.interprep.data.remote.api.GeminiApiImpl
import org.koin.dsl.module

internal actual object InternalDataModule {
    actual val module = module {
        factory<GeminiApi> { GeminiApiImpl() }
        factory<WebScrapper> { WebScrapperImpl() }
        factory<SqlDriver> {
            AndroidSqliteDriver(
                schema = DestructiveMigrationSchema,
                context = get(),
                name = "interprep.db"
            )
        }
    }
}
