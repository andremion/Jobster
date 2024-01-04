package io.github.andremion.jobster.ui.jobpostingsearch

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.andremion.boomerang.onUiEffect
import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.presentation.jobpostingsearch.JobPostingSearchPresenter
import io.github.andremion.jobster.presentation.jobpostingsearch.JobPostingSearchUiEffect
import io.github.andremion.jobster.presentation.jobpostingsearch.JobPostingSearchUiEvent
import io.github.andremion.jobster.presentation.jobpostingsearch.JobPostingSearchUiState
import io.github.andremion.jobster.ui.animation.LottieCompositionSpec
import io.github.andremion.jobster.ui.animation.rememberLottieComposition
import org.koin.compose.koinInject

@Composable
fun JobPostingSearchScreen(
    onNavigateToUrl: (url: String) -> Unit,
) {
    val presenter: JobPostingSearchPresenter =
        koinInject<JobPostingSearchPresenter>().apply { presenterScope = rememberCoroutineScope() }
    val uiState by presenter.uiState.collectAsState()
//    saveablePresenter { jobPostingSearchPresenter } collectUiState { presenter, uiState ->

//        presenter.launchInitialUiEvent { JobPostingSearchUiEvent.Init }
    LaunchedEffect(presenter) {
        presenter.onUiEvent(JobPostingSearchUiEvent.Init)
    }
//            .onUiEffect { uiEffect ->
    LaunchedEffect(presenter) {
        presenter.onUiEffect { uiEffect ->
            when (uiEffect) {
                is JobPostingSearchUiEffect.NavigateToUrl -> {
                    onNavigateToUrl(uiEffect.url)
                }
            }
        }
    }

    ScreenContent(
        uiState = uiState,
        onUiEvent = presenter::onUiEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    uiState: JobPostingSearchUiState,
    onUiEvent: (JobPostingSearchUiEvent) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(uiState.isSearchBarActive) {
        if (uiState.isSearchBarActive) {
            focusRequester.requestFocus()
        }
    }
    Scaffold(
        topBar = {
            val horizontalPadding by animateDpAsState(
                targetValue = if (uiState.isSearchBarActive) 0.dp else 16.dp,
                label = "searchBarHorizontalPaddingAnimation"
            )
            SearchBar(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .padding(horizontal = horizontalPadding)
                    .fillMaxWidth(),
                query = uiState.url,
                onQueryChange = { url ->
                    onUiEvent(JobPostingSearchUiEvent.UpdateUrl(url))
                },
                onSearch = {
                    onUiEvent(JobPostingSearchUiEvent.SearchClick)
                },
                active = uiState.isSearchBarActive,
                onActiveChange = { active ->
                    onUiEvent(JobPostingSearchUiEvent.UpdateSearchBarActive(isActive = active))
                },
                leadingIcon = {
                    if (uiState.isSearchBarActive) {
                        IconButton(
                            onClick = { onUiEvent(JobPostingSearchUiEvent.SearchBarBackClick) },
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Cancel Search",
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search",
                        )
                    }
                },
                trailingIcon = if (uiState.isSearchBarActive) {
                    {
                        IconButton(
                            onClick = { onUiEvent(JobPostingSearchUiEvent.ClearSearchClick) }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = "Clear Search",
                            )
                        }
                    }
                } else {
                    null
                },
                content = {
                    if (uiState.isLoading) {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth()
                        )
                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(name = "animation_job_posting_searching"))
                        LottieAnimation(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(200.dp),
                            composition = composition,
                        )
                    }
                    when (val error = uiState.error) {
                        is JobRepository.JobPostingSearchException ->
                            Error(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                message = error.message
                            )

                        is JobRepository.GeneralJobPostingSearchException ->
                            Error(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                message = "It seems our servers is taking too long to respond " +
                                    "or it might be an internet connection issue.\n" +
                                    "Please try again later. \uD83D\uDE4F\uD83C\uDFFB"
                            )
                    }
                },
            )
        },
    ) { innerPadding ->
        uiState.jobPosting?.let { jobPosting ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding),
                contentPadding = PaddingValues(vertical = 16.dp),
            ) {
                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                    ) {
                        // Temporary disable due to Gemini outdated responses
//                            AsyncImage(
//                                modifier = Modifier.size(80.dp),
//                                model = ImageRequest.Builder(context).data(jobPosting.logo).build(),
//                                placeholder = painterResource(R.drawable.ic_image),
//                                error = painterResource(R.drawable.ic_broken_image),
//                                contentDescription = "${jobPosting.company} logo",
//                            )
                        Column {
                            Text(
                                text = jobPosting.role,
                                style = MaterialTheme.typography.headlineMedium,
                            )
                            Text(
                                text = jobPosting.company,
                                style = MaterialTheme.typography.headlineSmall,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                }
                items(
                    items = jobPosting.contents,
                    key = JobPostingSearchUiState.JobPosting.Content::url
                ) { content ->
                    Row(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp,
                                start = 8.dp,
                                end = 16.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Temporary disable due to Gemini outdated responses
//                            AsyncImage(
//                                modifier = Modifier
//                                    .size(100.dp),
//                                model = ImageRequest.Builder(context).data(content.image).build(),
//                                placeholder = painterResource(R.drawable.ic_image),
//                                error = painterResource(R.drawable.ic_broken_image),
//                                contentDescription = "${content.title} image",
//                            )
                        Column(
                            modifier = Modifier
                                .weight(1f),
                        ) {
                            TextButton(
                                onClick = { onUiEvent(JobPostingSearchUiEvent.ContentTitleClick(content.url)) },
                            ) {
                                Text(
                                    text = content.title,
                                    textDecoration = TextDecoration.Underline,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = ButtonDefaults.TextButtonContentPadding
                                        .calculateLeftPadding(LocalLayoutDirection.current)
                                ),
                                text = content.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Switch(
                            checked = content.isChecked,
                            onCheckedChange = { isChecked ->
                                onUiEvent(
                                    JobPostingSearchUiEvent.ContentSwitchClick(
                                        content = content,
                                        isChecked = isChecked
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Error(
    modifier: Modifier,
    message: String
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(name = "animation_error"))
        LottieAnimation(
            modifier = Modifier.size(200.dp),
            composition = composition,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

//@Preview
//@Composable
//private fun ScreenContentSearchBarActivePreview() {
//    ScreenContent(
//        uiState = JobPostingSearchUiState.Initial.copy(
//            isSearchBarActive = true
//        ),
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun ScreenContentLoadingPreview() {
//    ScreenContent(
//        uiState = JobPostingSearchUiState.Initial.copy(
//            isSearchBarActive = true,
//            isLoading = true
//        ),
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun ScreenContentGeneralErrorPreview() {
//    ScreenContent(
//        uiState = JobPostingSearchUiState.Initial.copy(
//            isSearchBarActive = true,
//            error = JobRepository.GeneralJobPostingSearchException(
//                cause = RuntimeException()
//            )
//        ),
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun ScreenContentSearchErrorPreview() {
//    ScreenContent(
//        uiState = JobPostingSearchUiState.Initial.copy(
//            isSearchBarActive = true,
//            error = JobRepository.JobPostingSearchException(
//                message = "Something went wrong!",
//                cause = RuntimeException()
//            )
//        ),
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun ScreenContentResultPreview() {
//    ScreenContent(
//        uiState = JobPostingSearchUiState.Initial.copy(
//            url = "https://careers.google.com/jobs/results/134631828608854950-software-engineer-android/?company=Google&company=YouTube&employment_type=FULL_TIME&hl=en_US&jlo=en_US&q=android&sort_by=relevance",
//            jobPosting = JobPostingSearchUiState.JobPosting(
//                role = "Software Engineer - Android",
//                company = "Google",
//                logo = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
//                contents = listOf(
//                    JobPostingSearchUiState.JobPosting.Content(
//                        title = "How to prepare for a Google interview",
//                        description = "A guide to help you prepare for the technical aspects of Google interviews",
//                        url = "https://www.freecodecamp.org/news/how-to-prepare-for-a-google-software-engineer-interview-6d3c1cd3eae7/",
//                        image = "https://www.freecodecamp.org/news/content/images/2021/03/How-to-prepare-for-a-Google-interview.png",
//                        isChecked = true,
//                    ),
//                    JobPostingSearchUiState.JobPosting.Content(
//                        title = "How to be a great software engineer",
//                        description = "A guide to help you prepare for the non-technical aspects of Google interviews",
//                        url = "https://www.freecodecamp.org/news/how-to-be-a-great-software-engineer-3f5d44bc6f3e/",
//                        image = "https://www.freecodecamp.org/news/content/images/2021/03/How-to-be-a-great-software-engineer.png",
//                        isChecked = false,
//                    ),
//                )
//            ),
//        ),
//        onUiEvent = {}
//    )
//}
