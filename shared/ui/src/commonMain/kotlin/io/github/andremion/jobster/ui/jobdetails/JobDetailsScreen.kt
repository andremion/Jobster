package io.github.andremion.jobster.ui.jobdetails

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.andremion.boomerang.onUiEffect
import io.github.andremion.jobster.di.injectPresenter
import io.github.andremion.jobster.domain.entity.Job
import io.github.andremion.jobster.presentation.jobdetails.JobDetailsPresenter
import io.github.andremion.jobster.presentation.jobdetails.JobDetailsUiEffect
import io.github.andremion.jobster.presentation.jobdetails.JobDetailsUiEvent
import io.github.andremion.jobster.presentation.jobdetails.JobDetailsUiState
import io.github.andremion.jobster.ui.contentlist.ContentItem

@Composable
fun JobDetailsScreen(
    jobId: String,
    onNavigateToUrl: (url: String) -> Unit,
) {
    val presenter = injectPresenter(JobDetailsPresenter::class)

    LaunchedEffect(presenter) {
        presenter.onUiEvent(JobDetailsUiEvent.Init(jobId))

        presenter.onUiEffect { uiEffect ->
            when (uiEffect) {
                is JobDetailsUiEffect.NavigateToUrl -> {
                    onNavigateToUrl(uiEffect.url)
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
                ContentItem(
                    content = content,
                    onClick = { onUiEvent(JobDetailsUiEvent.ContentClick(content.url)) }
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun ScreenContentPreview() {
//    ScreenContent(
//        uiState = JobDetailsUiState.Initial.copy(
//            job = Job(
//                id = "1",
//                title = "Android Developer",
//                company = "Google",
//                url = "https://google.com",
//                logo = "https://google.com/logo.png",
//                contents = listOf(
//                    Job.Content(
//                        id = "1",
//                        title = "Content 1",
//                        description = "Content 1 description",
//                        url = "https://google.com",
//                        image = "https://google.com/logo.png",
//                    ),
//                    Job.Content(
//                        id = "2",
//                        title = "Content 2",
//                        description = "Content 2 description",
//                        url = "https://google.com",
//                        image = "https://google.com/logo.png",
//                    ),
//                    Job.Content(
//                        id = "3",
//                        title = "Content 3",
//                        description = "Content 3 description",
//                        url = "https://google.com",
//                        image = "https://google.com/logo.png",
//                    ),
//                )
//            )
//        ),
//        onUiEvent = {}
//    )
//}
