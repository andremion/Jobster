@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

package io.github.andremion.jobster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.andremion.jobster.ui.contentlist.ContentListScreen
import io.github.andremion.jobster.ui.jobdetails.JobDetailsScreen
import io.github.andremion.jobster.ui.joblist.JobListScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path

@Composable
fun HomeNavHost(
    modifier: Modifier,
    navigator: Navigator,
) {
    NavHost(
        modifier = modifier,
        navigator = navigator,
        initialRoute = "jobs"
    ) {
        scene(route = "jobs") {
            JobListScreen(
                onNavigateToJobDetails = { jobId ->
                    // jobId is basically the job posting url.
                    // we need to encode it in order to use it in the navigation route path,
                    // otherwise the navigation will fail to resolve.
                    navigator.navigate("jobs/${encodeUrl(jobId)}")
                }
            )
        }
        scene(route = "jobs/{job_id}") { backStackEntry ->
            val jobId = requireNotNull(backStackEntry.path<String>("job_id"))
            JobDetailsScreen(
                jobId = decodeUrl(jobId),
                onNavigateToUrl = ::navigateToUrl,
            )
        }
        scene(route = "contents") {
            ContentListScreen(
                onNavigateToUrl = ::navigateToUrl,
            )
        }
    }
}
