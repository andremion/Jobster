package io.github.andremion.interprep.data.di

import io.github.andremion.interprep.data.JobRepositoryImpl
import io.github.andremion.interprep.data.local.db.Database
import io.github.andremion.interprep.data.local.db.JobDao
import io.github.andremion.interprep.data.remote.JobPostingSearcher
import io.github.andremion.interprep.domain.JobRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

object DataModule {
    val module = module {
        includes(InternalDataModule.module)

        single {
            Database(
                driver = get()
            )
        }
        factory { get<Database>().jobQueries }
        factory { get<Database>().contentQueries }
        factory { get<Database>().jobContentQueries }
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
