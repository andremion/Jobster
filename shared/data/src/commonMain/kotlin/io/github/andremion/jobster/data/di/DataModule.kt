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

import io.github.andremion.jobster.data.JobRepositoryImpl
import io.github.andremion.jobster.data.local.db.JobDao
import io.github.andremion.jobster.data.local.db.JobsterDatabase
import io.github.andremion.jobster.data.remote.JobPostingSearcher
import io.github.andremion.jobster.data.remote.WebScrapper
import io.github.andremion.jobster.domain.JobRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

object DataModule {
    val module = module {
        includes(InternalDataModule.module)

        single {
            JobsterDatabase(
                driver = get()
            )
        }
        factory { get<JobsterDatabase>().jobQueries }
        factory { get<JobsterDatabase>().contentQueries }
        factory { get<JobsterDatabase>().jobContentQueries }
        factory {
            JobDao(
                dispatcher = Dispatchers.IO,
                jobQueries = get(),
                contentQueries = get(),
                jobContentQueries = get(),
            )
        }

        factory {
            WebScrapper(
                client = HttpClient()
            )
        }

        factory {
            JobPostingSearcher(
                webScrapper = get(),
                geminiApi = get(),
            )
        }

        single<JobRepository> {
            JobRepositoryImpl(
                jobDao = get(),
                jobPostingSearcher = get(),
            )
        }
    }
}
