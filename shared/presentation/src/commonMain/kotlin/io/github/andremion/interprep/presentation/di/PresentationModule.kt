package io.github.andremion.interprep.presentation.di

import io.github.andremion.interprep.presentation.contentlist.di.ContentListPresentationModule
import io.github.andremion.interprep.presentation.home.di.HomePresentationModule
import io.github.andremion.interprep.presentation.jobdetails.di.JobDetailsPresentationModule
import io.github.andremion.interprep.presentation.joblist.di.JobListPresentationModule
import io.github.andremion.interprep.presentation.jobpostingsearch.di.JobPostingSearchPresentationModule
import org.koin.dsl.module

object PresentationModule {
    val module = module {
        includes(HomePresentationModule.module)
        includes(JobPostingSearchPresentationModule.module)
        includes(JobListPresentationModule.module)
        includes(JobDetailsPresentationModule.module)
        includes(ContentListPresentationModule.module)
    }
}
