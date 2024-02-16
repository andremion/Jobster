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
