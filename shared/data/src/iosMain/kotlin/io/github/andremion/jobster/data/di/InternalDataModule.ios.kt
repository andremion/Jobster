package io.github.andremion.jobster.data.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.github.andremion.jobster.data.local.db.DatabaseFileName
import io.github.andremion.jobster.data.local.db.DestructiveMigrationSchema
import org.koin.dsl.module

internal actual object InternalDataModule {
    actual val module = module {
        factory<SqlDriver> {
            NativeSqliteDriver(
                schema = DestructiveMigrationSchema,
                name = DatabaseFileName
            )
        }
    }
}
