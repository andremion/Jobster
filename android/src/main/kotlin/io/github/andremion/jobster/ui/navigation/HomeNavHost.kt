package io.github.andremion.jobster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.andremion.jobster.ui.contentlist.ContentListScreen
import io.github.andremion.jobster.ui.jobdetails.JobDetailsScreen
import io.github.andremion.jobster.ui.joblist.JobListScreen
import java.net.URLEncoder

@Composable
fun HomeNavHost(
    modifier: Modifier,
    navController: NavHostController,
) {
    val context = LocalContext.current
    NavHost(
        modifier = modifier,
        navController = navController,
        route = "homeGraph",
        startDestination = "jobs"
    ) {
        composable(
            route = "jobs",
        ) {
            JobListScreen(
                onNavigateToJobDetails = { jobId ->
                    // jobId is basically the job posting url.
                    // we need to encode it in order to use it in the navigation route path,
                    // otherwise the navigation will fail to resolve.
                    navController.navigate("jobs/${URLEncoder.encode(jobId)}")
                }
            )
        }
        composable(
            route = "jobs/{job_id}",
        ) { backStackEntry ->
            val jobId = requireNotNull(backStackEntry.arguments?.getString("job_id"))
            JobDetailsScreen(
                jobId = jobId,
                onNavigateToUrl = context::navigateToUrl,
            )
        }
        composable(
            route = "contents",
        ) {
            ContentListScreen(
                onNavigateToUrl = context::navigateToUrl,
            )
        }
    }
}
