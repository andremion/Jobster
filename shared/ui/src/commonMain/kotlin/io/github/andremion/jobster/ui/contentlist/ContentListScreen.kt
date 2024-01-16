package io.github.andremion.jobster.ui.contentlist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import io.github.andremion.jobster.domain.entity.Job
import io.github.andremion.jobster.presentation.contentlist.ContentListUiEffect
import io.github.andremion.jobster.presentation.contentlist.ContentListUiEvent
import io.github.andremion.jobster.presentation.contentlist.ContentListUiState
import io.github.andremion.jobster.presentation.contentlist.ContentListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun ContentListScreen(
    onNavigateToUrl: (url: String) -> Unit,
) {
    val viewModel = koinViewModel(ContentListViewModel::class)

    val uiState by viewModel.uiState.collectAsState()

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

@Composable
private fun ScreenContent(
    uiState: ContentListUiState,
    onUiEvent: (ContentListUiEvent) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val error = uiState.error
    LaunchedEffect(error) {
        if (error != null) {
            snackbarHostState.showSnackbar(error.message ?: "Unknown error")
        }
    }
    uiState.contents?.let { items ->
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            items(
                items = items,
                key = Job.Content::id
            ) { content ->
                ContentItem(
                    content = content,
                    onClick = { onUiEvent(ContentListUiEvent.ContentClick(content.url)) }
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
