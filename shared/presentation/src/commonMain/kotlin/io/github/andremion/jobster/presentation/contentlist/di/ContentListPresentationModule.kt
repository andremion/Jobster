package io.github.andremion.jobster.presentation.contentlist.di

import io.github.andremion.jobster.presentation.contentlist.ContentListViewModel
import org.koin.dsl.module

internal object ContentListPresentationModule {
    val module = module {
        factory {
            ContentListViewModel(
                jobRepository = get()
            )
        }
    }
}
