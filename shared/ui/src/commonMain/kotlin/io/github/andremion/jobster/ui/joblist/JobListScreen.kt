package io.github.andremion.jobster.ui.joblist

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.andremion.boomerang.onUiEffect
import io.github.andremion.jobster.di.injectPresenter
import io.github.andremion.jobster.presentation.joblist.JobListPresenter
import io.github.andremion.jobster.presentation.joblist.JobListUiEffect
import io.github.andremion.jobster.presentation.joblist.JobListUiEvent
import io.github.andremion.jobster.presentation.joblist.JobListUiState

@Composable
fun JobListScreen(
    onNavigateToJobDetails: (jobId: String) -> Unit,
) {
    val presenter = injectPresenter(JobListPresenter::class)

    LaunchedEffect(presenter) {
        presenter.onUiEvent(JobListUiEvent.Init)

        presenter.onUiEffect { uiEffect ->
            when (uiEffect) {
                is JobListUiEffect.NavigateToJobDetails -> {
                    onNavigateToJobDetails(uiEffect.jobId)
                }
            }
        }
    }

    val uiState by presenter.uiState.collectAsState()

    ScreenContent(
        uiState = uiState,
        onUiEvent = presenter::onUiEvent,
    )
}

@Composable
private fun ScreenContent(
    uiState: JobListUiState,
    onUiEvent: (JobListUiEvent) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val error = uiState.error
    LaunchedEffect(error) {
        if (error != null) {
            snackbarHostState.showSnackbar(error.message ?: "Unknown error")
        }
    }
    val items = uiState.jobs
    if (items != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = items,
            ) { job ->
                Card(
                    modifier = Modifier.clickable {
                        onUiEvent(JobListUiEvent.JobClick(job.id))
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = job.title,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Text(
                            text = job.company,
                            style = MaterialTheme.typography.bodySmall
                        )
                        job.contents?.let { contents ->
                            Text(
                                text = contents,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun ScreenContentPreview() {
//    ScreenContent(
//        uiState = JobListUiState.Initial.copy(
//            jobs = listOf(
//                JobListUiState.Job(
//                    id = "1",
//                    title = "Job title 1",
//                    company = "Company name 1",
//                    url = "https://www.google.com",
//                    logo = "https://www.google.com",
//                    contents = "Content title 1, Content title 2",
//                ),
//                JobListUiState.Job(
//                    id = "2",
//                    title = "Job title 2",
//                    company = "Company name 2",
//                    url = "https://www.google.com",
//                    logo = "https://www.google.com",
//                    contents = "Content title 3, Content title 4, Content title 5, Content title 6, " +
//                        "Content title 7, Content title 8, Content title 9",
//                ),
//            ),
//        ),
//        onUiEvent = {},
//    )
//}
