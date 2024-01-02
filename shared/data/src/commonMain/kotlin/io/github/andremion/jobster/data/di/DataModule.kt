package io.github.andremion.jobster.data.di

import io.github.andremion.jobster.data.JobRepositoryImpl
import io.github.andremion.jobster.data.local.db.JobDao
import io.github.andremion.jobster.data.local.db.JobsterDatabase
import io.github.andremion.jobster.data.remote.JobPostingSearcher
import io.github.andremion.jobster.domain.JobRepository
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
