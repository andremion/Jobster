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

package io.github.andremion.jobster.presentation.home

import io.github.andremion.jobster.domain.entity.SearchResult

sealed interface HomeUiEvent {
    data object AddContentClick : HomeUiEvent
    data class UpdateQuery(val query: String) : HomeUiEvent
    data class UpdateSearchBarActive(val isActive: Boolean) : HomeUiEvent
    data object SearchBarBackClick : HomeUiEvent
    data object ClearSearchClick : HomeUiEvent
    data class SearchResultClick(
        val type: SearchResult.Type,
        val id: String,
        val url: String,
    ) : HomeUiEvent
}
