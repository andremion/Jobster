package io.github.andremion.jobster.presentation.di

import io.github.andremion.jobster.presentation.contentlist.di.ContentListPresentationModule
import io.github.andremion.jobster.presentation.home.di.HomePresentationModule
import io.github.andremion.jobster.presentation.jobdetails.di.JobDetailsPresentationModule
import io.github.andremion.jobster.presentation.joblist.di.JobListPresentationModule
import io.github.andremion.jobster.presentation.jobpostingsearch.di.JobPostingSearchPresentationModule
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
