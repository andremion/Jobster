package io.github.andremion.interprep.ui.contentlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.andremion.boomerang.android.collectUiState
import io.github.andremion.boomerang.android.launchInitialUiEvent
import io.github.andremion.boomerang.android.onUiEffect
import io.github.andremion.boomerang.android.saveablePresenter
import io.github.andremion.interprep.presentation.contentlist.ContentListPresenter
import io.github.andremion.interprep.presentation.contentlist.ContentListUiEffect
import io.github.andremion.interprep.presentation.contentlist.ContentListUiEvent
import io.github.andremion.interprep.presentation.contentlist.ContentListUiState
import org.koin.compose.koinInject

@Composable
fun ContentListScreen(
    onNavigateToUrl: (url: String) -> Unit,
) {
    val contentListPresenter: ContentListPresenter = koinInject()
    saveablePresenter { contentListPresenter } collectUiState { presenter, uiState ->

        presenter.launchInitialUiEvent { ContentListUiEvent.Init }
            .onUiEffect { uiEffect ->
                when (uiEffect) {
                    is ContentListUiEffect.NavigateToUrl -> {
                        onNavigateToUrl(uiEffect.url)
                    }
                }
            }

        ScreenContent(
            uiState = uiState,
            onUiEvent = presenter::onUiEvent,
        )
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
    val items = uiState.contents
    if (items != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = items,
            ) { content ->
                Card(
                    modifier = Modifier.clickable {
                        onUiEvent(ContentListUiEvent.ContentClick(content.url))
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = content.title,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Text(
                            text = content.description,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenContentPreview() {
    ScreenContent(
        uiState = ContentListUiState.Initial.copy(
            contents = listOf(
                ContentListUiState.Content(
                    id = "1",
                    title = "Content title 1",
                    description = "Content description 1",
                    url = "https://www.google.com",
                    image = "https://www.google.com",
                ),
                ContentListUiState.Content(
                    id = "2",
                    title = "Content title 2",
                    description = "Content description 2",
                    url = "https://www.google.com",
                    image = "https://www.google.com",
                ),
            ),
        ),
        onUiEvent = {},
    )
}

