package io.github.andremion.interprep.ui.jobdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.andremion.boomerang.android.collectUiState
import io.github.andremion.boomerang.android.launchInitialUiEvent
import io.github.andremion.boomerang.android.onUiEffect
import io.github.andremion.boomerang.android.saveablePresenter
import io.github.andremion.interprep.domain.entity.Job
import io.github.andremion.interprep.presentation.jobdetails.JobDetailsPresenter
import io.github.andremion.interprep.presentation.jobdetails.JobDetailsUiEffect
import io.github.andremion.interprep.presentation.jobdetails.JobDetailsUiEvent
import io.github.andremion.interprep.presentation.jobdetails.JobDetailsUiState
import org.koin.compose.koinInject

@Composable
fun JobDetailsScreen(
    jobId: String,
    onNavigateToUrl: (url: String) -> Unit,
) {
    val jobDetailsPresenter: JobDetailsPresenter = koinInject()
    saveablePresenter { jobDetailsPresenter } collectUiState { presenter, uiState ->

        presenter.launchInitialUiEvent { JobDetailsUiEvent.Init(jobId) }
            .onUiEffect { uiEffect ->
                when (uiEffect) {
                    is JobDetailsUiEffect.NavigateToUrl -> {
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
    uiState: JobDetailsUiState,
    onUiEvent: (JobDetailsUiEvent) -> Unit,
) {
    uiState.job?.let { job ->
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                ) {
                    Column {
                        Text(
                            text = job.title,
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Text(
                            text = job.company,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
            items(
                items = job.contents,
                key = Job.Content::id
            ) { content ->
                Row(
                    modifier = Modifier
                        .clickable {
                            onUiEvent(JobDetailsUiEvent.ContentClick(content.url))
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
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
        uiState = JobDetailsUiState.Initial.copy(
            job = Job(
                id = "1",
                title = "Android Developer",
                company = "Google",
                url = "https://google.com",
                logo = "https://google.com/logo.png",
                contents = listOf(
                    Job.Content(
                        id = "1",
                        title = "Content 1",
                        description = "Content 1 description",
                        url = "https://google.com",
                        image = "https://google.com/logo.png",
                    ),
                    Job.Content(
                        id = "2",
                        title = "Content 2",
                        description = "Content 2 description",
                        url = "https://google.com",
                        image = "https://google.com/logo.png",
                    ),
                    Job.Content(
                        id = "3",
                        title = "Content 3",
                        description = "Content 3 description",
                        url = "https://google.com",
                        image = "https://google.com/logo.png",
                    ),
                )
            )
        ),
        onUiEvent = {}
    )
}
