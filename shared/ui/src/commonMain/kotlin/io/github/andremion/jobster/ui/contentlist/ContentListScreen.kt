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

package io.github.andremion.jobster.ui.contentlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.andremion.jobster.domain.entity.Job
import io.github.andremion.jobster.presentation.contentlist.ContentListUiEffect
import io.github.andremion.jobster.presentation.contentlist.ContentListUiEvent
import io.github.andremion.jobster.presentation.contentlist.ContentListUiState
import io.github.andremion.jobster.presentation.contentlist.ContentListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun ContentListScreen(
    onNavigateToUrl: (url: String) -> Unit,
) {
    val viewModel = koinViewModel(ContentListViewModel::class)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScreenContent(
        uiState = uiState,
        onUiEvent = viewModel::onUiEvent,
    )

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.onEach { uiEffect ->
            when (uiEffect) {
                is ContentListUiEffect.NavigateToUrl -> {
                    onNavigateToUrl(uiEffect.url)
                }
            }
        }.launchIn(this)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ScreenContent(
    uiState: ContentListUiState,
    onUiEvent: (ContentListUiEvent) -> Unit,
) {
    uiState.contents?.let { items ->
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            items(
                items = items,
                key = Job.Content::id
            ) { content ->
                ContentItem(
                    modifier = Modifier
                        .animateItemPlacement(),
                    content = content,
                    onClick = { onUiEvent(ContentListUiEvent.ContentClick(content.url)) },
                    onSwipeToDelete = { onUiEvent(ContentListUiEvent.DeleteContent(content.id)) },
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun ScreenContentPreview() {
//    ScreenContent(
//        uiState = ContentListUiState.Initial.copy(
//            contents = listOf(
//                ContentListUiState.Content(
//                    id = "1",
//                    title = "Content title 1",
//                    description = "Content description 1",
//                    url = "https://www.google.com",
//                    image = "https://www.google.com",
//                ),
//                ContentListUiState.Content(
//                    id = "2",
//                    title = "Content title 2",
//                    description = "Content description 2",
//                    url = "https://www.google.com",
//                    image = "https://www.google.com",
//                ),
//            ),
//        ),
//        onUiEvent = {},
//    )
//}
//
