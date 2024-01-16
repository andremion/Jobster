package io.github.andremion.jobster.ui.jobpostingsearch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.andremion.jobster.domain.exception.JobPostingSearchException
import io.github.andremion.jobster.presentation.jobpostingsearch.JobPostingSearchUiEffect
import io.github.andremion.jobster.presentation.jobpostingsearch.JobPostingSearchUiEvent
import io.github.andremion.jobster.presentation.jobpostingsearch.JobPostingSearchUiState
import io.github.andremion.jobster.presentation.jobpostingsearch.JobPostingSearchViewModel
import io.github.andremion.jobster.ui.animation.LottieCompositionSpec
import io.github.andremion.jobster.ui.animation.rememberLottieComposition
import io.github.andremion.jobster.ui.component.BoxWithBackground
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun JobPostingSearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToUrl: (url: String) -> Unit,
) {
    val viewModel = koinViewModel(JobPostingSearchViewModel::class)

    val uiState by viewModel.uiState.collectAsState()

    ScreenContent(
        uiState = uiState,
        onUiEvent = viewModel::onUiEvent,
    )

    LaunchedEffect(uiState) {
        viewModel.uiEffect.onEach { uiEffect ->
            when (uiEffect) {
                is JobPostingSearchUiEffect.NavigateBack -> {
                    onNavigateBack()
                }

                is JobPostingSearchUiEffect.NavigateToUrl -> {
                    onNavigateToUrl(uiEffect.url)
                }
            }
        }.launchIn(this)
    }
}

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
            SearchBar(
                modifier = Modifier.focusRequester(focusRequester),
                uiState = uiState,
                onUiEvent = onUiEvent
            )
        },
    ) { innerPadding ->
        BoxWithBackground(
            modifier = Modifier.fillMaxSize(),
        ) {
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    modifier: Modifier,
    uiState: JobPostingSearchUiState,
    onUiEvent: (JobPostingSearchUiEvent) -> Unit
) {
    val horizontalPadding by animateDpAsState(
        targetValue = if (uiState.isSearchBarActive) 0.dp else 16.dp,
        label = "searchBarHorizontalPaddingAnimation"
    )
    SearchBar(
        modifier = modifier
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
        placeholder = {
            Text(text = "Enter a job posting URL")
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
                IconButton(
                    onClick = { onUiEvent(JobPostingSearchUiEvent.BackClick) },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back to Home",
                    )
                }
            }
        },
        trailingIcon = {
            if (uiState.isSearchBarActive) {
                IconButton(
                    onClick = { onUiEvent(JobPostingSearchUiEvent.SearchBarClearClick) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "Clear Search",
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
                )
            }
        },
    ) {
        SearchBarContent(
            isGeminiLogoVisible = uiState.error == null
        ) {
            if (uiState.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
                val composition by rememberLottieComposition(
                    spec = LottieCompositionSpec.AnimationRes(name = "job_posting_search_loading")
                )
                LottieAnimation(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(200.dp),
                    composition = composition,
                )
            }
            when (val error = uiState.error) {
                is JobPostingSearchException.Server ->
                    Error(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        message = error.message
                    )

                is JobPostingSearchException.General ->
                    Error(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        message = "It might be an internet connection issue\n" +
                            "or the URL you entered is incorrect."
                    )

                null -> {
                    // no-op
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SearchBarContent(
    isGeminiLogoVisible: Boolean,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            content(this)
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomEnd),
            visible = isGeminiLogoVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CompositionLocalProvider(
                LocalContentColor provides LocalContentColor.current.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "Powered by",
                        style = MaterialTheme.typography.labelLarge,
                    )
                    Image(
                        painter = painterResource("images/ic_gemini.xml"),
                        contentDescription = "Gemini"
                    )
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
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.AnimationRes(name = "job_posting_search_error")
        )
        LottieAnimation(
            modifier = Modifier.size(200.dp),
            composition = composition,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
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
