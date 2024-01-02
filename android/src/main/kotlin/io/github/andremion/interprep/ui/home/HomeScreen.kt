package io.github.andremion.interprep.ui.home

import androidx.annotation.StringRes
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.andremion.boomerang.android.collectUiState
import io.github.andremion.boomerang.android.launchInitialUiEvent
import io.github.andremion.boomerang.android.onUiEffect
import io.github.andremion.boomerang.android.saveablePresenter
import io.github.andremion.interprep.R
import io.github.andremion.interprep.domain.entity.SearchResult
import io.github.andremion.interprep.presentation.home.HomePresenter
import io.github.andremion.interprep.presentation.home.HomeUiEffect
import io.github.andremion.interprep.presentation.home.HomeUiEvent
import io.github.andremion.interprep.presentation.home.HomeUiState
import io.github.andremion.interprep.ui.animation.BottomBarAnimatedVisibility
import io.github.andremion.interprep.ui.animation.FabAnimatedVisibility
import io.github.andremion.interprep.ui.animation.TopBarAnimatedVisibility
import io.github.andremion.interprep.ui.navigation.HomeNavHost
import io.github.andremion.interprep.ui.navigation.contains
import io.github.andremion.interprep.ui.navigation.navigateSingleTopTo
import org.koin.compose.koinInject

private enum class NavigationItem(
    val route: String,
    @StringRes val label: Int,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
) {
    Jobs(
        route = "jobs",
        label = R.string.home_navigation_job_list_label,
        unselectedIcon = Icons.Rounded.WorkOutline,
        selectedIcon = Icons.Rounded.Work,
    ),
    Contents(
        route = "contents",
        label = R.string.home_navigation_content_list_label,
        unselectedIcon = Icons.Rounded.Segment,
        selectedIcon = Icons.Rounded.Sort,
    ),
}

@Composable
fun HomeScreen(
    navController: NavHostController,
    onNavigateToJobPostingSearch: () -> Unit,
    onNavigateToJobDetails: (jobId: String) -> Unit,
    onNavigateToUrl: (url: String) -> Unit,
) {
    val homePresenter: HomePresenter = koinInject()
    saveablePresenter { homePresenter } collectUiState { presenter, uiState ->

        presenter.launchInitialUiEvent { HomeUiEvent.Init }
            .onUiEffect { uiEffect ->
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

        ScreenContent(
            navController = navController,
            uiState = uiState,
            onUiEvent = presenter::onUiEvent,
        )
    }
}

@Composable
private fun ScreenContent(
    navController: NavHostController,
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
                        contentDescription = stringResource(id = R.string.home_fab_add_content_button)
                    )
                }
            }
        },
        bottomBar = {
            BottomBarAnimatedVisibility(isVisible = isNavigationBarVisible) {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    NavigationItem.entries.forEach { item ->
                        val isSelected = currentDestination?.contains(item.route) == true
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
                                Text(text = stringResource(id = item.label))
                            },
                            selected = isSelected,
                            onClick = {
                                navController.navigateSingleTopTo(item.route)
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        HomeNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
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
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_empty))
            LottieAnimation(
                modifier = Modifier.size(200.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )
            Text(
                text = stringResource(id = R.string.home_search_results_empty),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun SearchBarInitialPreview() {
    SearchBar(
        query = HomeUiState.Initial.query,
        isSearchBarActive = HomeUiState.Initial.isSearchBarActive,
        searchResults = HomeUiState.Initial.searchResults,
        onUiEvent = {}
    )
}

@Preview
@Composable
private fun SearchBarActivePreview() {
    SearchBar(
        query = HomeUiState.Initial.query,
        isSearchBarActive = true,
        searchResults = HomeUiState.Initial.searchResults,
        onUiEvent = {}
    )
}

@Preview
@Composable
private fun SearchBarTypingPreview() {
    SearchBar(
        query = "Searching...",
        isSearchBarActive = true,
        searchResults = HomeUiState.Initial.searchResults,
        onUiEvent = {}
    )
}

@Preview
@Composable
private fun SearchBarResultsPreview() {
    SearchBar(
        query = "Searching...",
        isSearchBarActive = true,
        searchResults = listOf(
            SearchResult(
                id = "1",
                type = SearchResult.Type.Job,
                title = "Job title 1",
                description = "Job description 1",
                url = "https://www.google.com/job",
            ),
            SearchResult(
                id = "2",
                type = SearchResult.Type.Content,
                title = "Content title 1",
                description = "Content description 1",
                url = "https://www.google.com/content",
            ),
        ),
        onUiEvent = {}
    )
}

@Preview
@Composable
private fun SearchBarEmptyResultPreview() {
    SearchBar(
        query = "Searching...",
        isSearchBarActive = true,
        searchResults = emptyList(),
        onUiEvent = {}
    )
}
