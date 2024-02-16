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

package io.github.andremion.jobster.presentation.jobdetails

import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.presentation.AbsViewModel
import io.github.andremion.jobster.presentation.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class JobDetailsViewModel(
    private val jobId: String,
    private val jobRepository: JobRepository
) : AbsViewModel<JobDetailsUiState, JobDetailsUiEvent, JobDetailsUiEffect>() {

    override val uiState: StateFlow<JobDetailsUiState> = jobRepository.getJobById(jobId)
        .map { job ->
            JobDetailsUiState(
                isLoading = false,
                job = job,
            )
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed,
            initialValue = JobDetailsUiState(isLoading = true)
        )

    override fun onUiEvent(uiEvent: JobDetailsUiEvent) {
        when (uiEvent) {
            is JobDetailsUiEvent.ContentClick -> {
                mutableUiEffect.tryEmit(JobDetailsUiEffect.NavigateToUrl(uiEvent.url))
            }

            is JobDetailsUiEvent.DeleteContent -> {
                viewModelScope.launch {
                    jobRepository.delete(jobId, uiEvent.contentId)
                }
            }
        }
    }
}
