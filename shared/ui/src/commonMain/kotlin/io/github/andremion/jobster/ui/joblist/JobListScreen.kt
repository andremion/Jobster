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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.andremion.jobster.presentation.joblist.JobListUiEffect
import io.github.andremion.jobster.presentation.joblist.JobListUiEvent
import io.github.andremion.jobster.presentation.joblist.JobListUiState
import io.github.andremion.jobster.presentation.joblist.JobListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun JobListScreen(
    onNavigateToJobDetails: (jobId: String) -> Unit,
) {
    val viewModel = koinViewModel(JobListViewModel::class)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScreenContent(
        uiState = uiState,
        onUiEvent = viewModel::onUiEvent,
    )

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.onEach { uiEffect ->
            when (uiEffect) {
                is JobListUiEffect.NavigateToJobDetails -> {
                    onNavigateToJobDetails(uiEffect.jobId)
                }
            }
        }.launchIn(this)
    }
}

@Composable
private fun ScreenContent(
    uiState: JobListUiState,
    onUiEvent: (JobListUiEvent) -> Unit,
) {
    uiState.jobs?.let { items ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = items,
                key = JobListUiState.Job::id
            ) { job ->
                Card(
                    shape = RoundedCornerShape(
                        topEnd = CardCornerSize,
                        bottomStart = CardCornerSize
                    ),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .clickable { onUiEvent(JobListUiEvent.JobClick(job.id)) }
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
                        job.content?.let { contents ->
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

private val CardCornerSize = 16.dp

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
