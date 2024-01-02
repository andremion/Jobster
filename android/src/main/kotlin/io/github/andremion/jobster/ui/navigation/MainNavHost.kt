package io.github.andremion.jobster.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.andremion.jobster.ui.home.HomeScreen
import io.github.andremion.jobster.ui.jobpostingsearch.JobPostingSearchScreen
import java.net.URLEncoder

@Composable
fun MainNavHost() {
    val context = LocalContext.current
    val mainNavController = rememberNavHostController()
    val homeNavController = rememberNavHostController()
    NavHost(
        navController = mainNavController,
        route = "mainGraph",
        startDestination = "home",
        enterTransition = { slideIntoContainer(towards = SlideDirection.Start) },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { slideOutOfContainer(towards = SlideDirection.End) },
    ) {
        composable(
            route = "home",
        ) {
            HomeScreen(
                navController = homeNavController,
                onNavigateToJobPostingSearch = {
                    mainNavController.navigate("job_posting_search")
                },
                onNavigateToJobDetails = { jobId ->
                    // jobId is basically the job posting url.
                    // we need to encode it in order to use it in the navigation route path,
                    // otherwise the navigation will fail to resolve.
                    homeNavController.navigate("jobs/${URLEncoder.encode(jobId)}")
                },
                onNavigateToUrl = context::navigateToUrl,
            )
        }
        composable(
            route = "job_posting_search",
        ) {
            JobPostingSearchScreen(
                onNavigateToUrl = context::navigateToUrl,
            )
        }
    }
}
