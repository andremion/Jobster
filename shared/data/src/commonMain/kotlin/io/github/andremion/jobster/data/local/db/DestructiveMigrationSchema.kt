package io.github.andremion.jobster.data.local.db

import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

internal const val DatabaseFileName = "jobster.db"

internal object DestructiveMigrationSchema : SqlSchema<QueryResult.Value<Unit>> by JobsterDatabase.Schema {

    override fun migrate(
        driver: SqlDriver,
        oldVersion: Long,
        newVersion: Long,
        vararg callbacks: AfterVersion
    ): QueryResult.Value<Unit> {
        val tables = driver.executeQuery(
            identifier = null,
            sql = "SELECT name FROM sqlite_master WHERE type='table';",
            parameters = 0,
            mapper = { cursor ->
                QueryResult.Value(buildList {
                    while (cursor.next().value) {
                        val name = cursor.getString(0)!!
                        if (name != "sqlite_sequence" && name != "android_metadata") {
                            add(name)
                        }
                    }
                })
            }
        ).value

        for (table in tables) {
            driver.execute(identifier = null, sql = "DROP TABLE $table", parameters = 0)
        }

        return create(driver)
    }
}
