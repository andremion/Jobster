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
