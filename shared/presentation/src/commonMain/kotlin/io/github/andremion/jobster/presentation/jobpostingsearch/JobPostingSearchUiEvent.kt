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

package io.github.andremion.jobster.presentation.jobpostingsearch

sealed interface JobPostingSearchUiEvent {
    data object BackClick : JobPostingSearchUiEvent
    data class UpdateUrl(val url: String) : JobPostingSearchUiEvent
    data class UpdateSearchBarActive(val isActive: Boolean) : JobPostingSearchUiEvent
    data object SearchBarBackClick : JobPostingSearchUiEvent
    data object SearchBarClearClick : JobPostingSearchUiEvent
    data object SearchClick : JobPostingSearchUiEvent
    data class ContentTitleClick(val url: String) : JobPostingSearchUiEvent
    data class ContentSwitchClick(
        val content: JobPostingSearchUiState.JobPosting.Content,
        val isChecked: Boolean
    ) : JobPostingSearchUiEvent
}
