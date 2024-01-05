package io.github.andremion.jobster.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.PostAdd
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Segment
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material.icons.rounded.WorkOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieConstants
import io.github.andremion.boomerang.onUiEffect
import io.github.andremion.jobster.domain.entity.SearchResult
import io.github.andremion.jobster.presentation.home.HomePresenter
import io.github.andremion.jobster.presentation.home.HomeUiEffect
import io.github.andremion.jobster.presentation.home.HomeUiEvent
import io.github.andremion.jobster.presentation.home.HomeUiState
import io.github.andremion.jobster.ui.animation.BottomBarAnimatedVisibility
import io.github.andremion.jobster.ui.animation.FabAnimatedVisibility
import io.github.andremion.jobster.ui.animation.LottieCompositionSpec
import io.github.andremion.jobster.ui.animation.TopBarAnimatedVisibility
import io.github.andremion.jobster.ui.animation.rememberLottieComposition
import io.github.andremion.jobster.ui.navigation.HomeNavHost
import io.github.andremion.jobster.ui.navigation.navigateSingleTopTo
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

private enum class NavigationItem(
    val route: String,
    val label: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
) {
    Jobs(
        route = "jobs",
        label = "Jobs",
        unselectedIcon = Icons.Rounded.WorkOutline,
        selectedIcon = Icons.Rounded.Work,
    ),
    Contents(
        route = "contents",
        label = "Contents",
        unselectedIcon = Icons.Rounded.Segment,
        selectedIcon = Icons.Rounded.Sort,
    ),
}

@Composable
fun HomeScreen(
    navigator: Navigator,
    onNavigateToJobPostingSearch: () -> Unit,
    onNavigateToJobDetails: (jobId: String) -> Unit,
    onNavigateToUrl: (url: String) -> Unit,
) {
    val presenter: HomePresenter = koinInject<HomePresenter>().apply { presenterScope = rememberCoroutineScope() }
    val uiState by presenter.uiState.collectAsState()
//    saveablePresenter { homePresenter } collectUiState { presenter, uiState ->

//        presenter.launchInitialUiEvent { HomeUiEvent.Init }
    LaunchedEffect(presenter) {
        presenter.onUiEvent(HomeUiEvent.Init)
    }

//            .onUiEffect { uiEffect ->
    LaunchedEffect(presenter) {
        presenter.onUiEffect { uiEffect ->
            when (uiEffect) {
                is HomeUiEffect.NavigateToJobPostingSearch -> {
                    onNavigateToJobPostingSearch()
                }

                is HomeUiEffect.NavigateToJobDetails -> {
                    onNavigateToJobDetails(uiEffect.jobId)
                }

                is HomeUiEffect.NavigateToUrl -> {
                    onNavigateToUrl(uiEffect.url)
                }
            }
        }
    }

    ScreenContent(
        navigator = navigator,
        uiState = uiState,
        onUiEvent = presenter::onUiEvent,
    )
}

@Composable
private fun ScreenContent(
    navigator: Navigator,
    uiState: HomeUiState,
    onUiEvent: (HomeUiEvent) -> Unit,
) {
    val isNavigationBarVisible = !uiState.isSearchBarActive
    Scaffold(
        topBar = {
            TopBarAnimatedVisibility(isVisible = true) {
                SearchBar(
                    query = uiState.query,
                    isSearchBarActive = uiState.isSearchBarActive,
                    searchResults = uiState.searchResults,
                    onUiEvent = onUiEvent
                )
            }
        },
        floatingActionButton = {
            FabAnimatedVisibility(isVisible = isNavigationBarVisible) {
                ExtendedFloatingActionButton(
                    onClick = {
                        onUiEvent(HomeUiEvent.AddContentClick)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PostAdd,
                        contentDescription = "Add content"
                    )
                }
            }
        },
        bottomBar = {
            BottomBarAnimatedVisibility(isVisible = isNavigationBarVisible) {
                NavigationBar {
//                    val navBackStackEntry by navigator.currentBackStackEntryAsState()
                    val navBackStackEntry by navigator.currentEntry.collectAsState(null)
                    val currentRoute = navBackStackEntry?.route
                    NavigationItem.entries.forEach { item ->
                        val isSelected = currentRoute?.route == item.route
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) {
                                        item.selectedIcon
                                    } else {
                                        item.unselectedIcon
                                    },
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = item.label)
                            },
                            selected = isSelected,
                            onClick = {
                                navigator.navigateSingleTopTo(item.route)
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        HomeNavHost(
            modifier = Modifier.padding(innerPadding),
            navigator = navigator,
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchBar(
    query: String,
    isSearchBarActive: Boolean,
    searchResults: List<SearchResult>?,
    onUiEvent: (HomeUiEvent) -> Unit
) {
    val animated by animateDpAsState(
        targetValue = if (isSearchBarActive) 0.dp else 16.dp,
        label = "searchBarHorizontalPaddingAnimation"
    )
    SearchBar(
        modifier = Modifier
            .padding(horizontal = animated)
            .fillMaxWidth(),
        query = query,
        onQueryChange = { changedQuery ->
            onUiEvent(HomeUiEvent.UpdateQuery(changedQuery))
        },
        onSearch = { /* Search is done on each query change */ },
        active = isSearchBarActive,
        onActiveChange = { active ->
            onUiEvent(HomeUiEvent.UpdateSearchBarActive(isActive = active))
        },
        leadingIcon = {
            if (isSearchBarActive) {
                IconButton(
                    onClick = { onUiEvent(HomeUiEvent.SearchBarBackClick) },
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
        trailingIcon = if (isSearchBarActive) {
            {
                IconButton(
                    onClick = { onUiEvent(HomeUiEvent.ClearSearchClick) },
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
    ) {
        EmptySearchResults(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            isVisible = searchResults?.isEmpty() == true
        )
        if (searchResults != null) {
            LazyColumn {
                items(searchResults, key = SearchResult::id) { searchResult ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onUiEvent(
                                    HomeUiEvent.SearchResultClick(
                                        type = searchResult.type,
                                        id = searchResult.id,
                                        url = searchResult.url,
                                    )
                                )
                            }
                            .padding(8.dp)
                    ) {
                        when (searchResult.type) {
                            SearchResult.Type.Job -> {
                                Icon(
                                    modifier = Modifier.padding(end = 8.dp),
                                    imageVector = NavigationItem.Jobs.unselectedIcon,
                                    contentDescription = "Job",
                                )
                            }

                            SearchResult.Type.Content -> {
                                Icon(
                                    modifier = Modifier.padding(end = 8.dp),
                                    imageVector = NavigationItem.Contents.unselectedIcon,
                                    contentDescription = "Content",
                                )
                            }
                        }
                        Column {
                            Text(
                                text = searchResult.title,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = searchResult.description,
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

@Composable
private fun EmptySearchResults(
    modifier: Modifier,
    isVisible: Boolean
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.AnimationRes(name = "search_results_empty"))
            LottieAnimation(
                modifier = Modifier.size(200.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )
            Text(
                text = "No content found! \uD83D\uDE41",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

//@Preview
//@Composable
//private fun SearchBarInitialPreview() {
//    SearchBar(
//        query = HomeUiState.Initial.query,
//        isSearchBarActive = HomeUiState.Initial.isSearchBarActive,
//        searchResults = HomeUiState.Initial.searchResults,
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun SearchBarActivePreview() {
//    SearchBar(
//        query = HomeUiState.Initial.query,
//        isSearchBarActive = true,
//        searchResults = HomeUiState.Initial.searchResults,
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun SearchBarTypingPreview() {
//    SearchBar(
//        query = "Searching...",
//        isSearchBarActive = true,
//        searchResults = HomeUiState.Initial.searchResults,
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun SearchBarResultsPreview() {
//    SearchBar(
//        query = "Searching...",
//        isSearchBarActive = true,
//        searchResults = listOf(
//            SearchResult(
//                id = "1",
//                type = SearchResult.Type.Job,
//                title = "Job title 1",
//                description = "Job description 1",
//                url = "https://www.google.com/job",
//            ),
//            SearchResult(
//                id = "2",
//                type = SearchResult.Type.Content,
//                title = "Content title 1",
//                description = "Content description 1",
//                url = "https://www.google.com/content",
//            ),
//        ),
//        onUiEvent = {}
//    )
//}
//
//@Preview
//@Composable
//private fun SearchBarEmptyResultPreview() {
//    SearchBar(
//        query = "Searching...",
//        isSearchBarActive = true,
//        searchResults = emptyList(),
//        onUiEvent = {}
//    )
//}
