package io.github.andremion.jobster.ui.navigation

import androidx.compose.runtime.Composable
import io.github.andremion.jobster.ui.home.HomeScreen
import io.github.andremion.jobster.ui.jobpostingsearch.JobPostingSearchScreen
import moe.tlaster.precompose.navigation.NavHost

@Composable
fun MainNavHost() {
    val mainNavigator = rememberNavigator(name = "main")
    val homeNavigator = rememberNavigator(name = "home")
    NavHost(
        navigator = mainNavigator,
        initialRoute = "home",
    ) {
        scene(route = "home") {
            HomeScreen(
                navigator = homeNavigator,
                onNavigateToJobPostingSearch = {
                    mainNavigator.navigate("job_posting_search")
                },
                onNavigateToJobDetails = { jobId ->
                    // jobId is basically the job posting url.
                    // we need to encode it in order to use it in the navigation route path,
                    // otherwise the navigation will fail to resolve.
                    homeNavigator.navigate("jobs/${encodeUrl(jobId)}")
                },
                onNavigateToUrl = ::navigateToUrl,
            )
        }
        scene(route = "job_posting_search") {
            JobPostingSearchScreen(
                onNavigateBack = mainNavigator::goBack,
                onNavigateToUrl = ::navigateToUrl,
            )
        }
    }
}
