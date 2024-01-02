package io.github.andremion.interprep.presentation.contentlist.di

import io.github.andremion.interprep.presentation.contentlist.ContentListPresenter
import org.koin.dsl.module

internal object ContentListPresentationModule {
    val module = module {
        factory {
            ContentListPresenter(
                jobRepository = get()
            )
        }
    }
}
