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

package io.github.andremion.jobster.data.remote.model

import kotlinx.serialization.Serializable

// Not internal because it is used in Swift too
@Serializable
data class GetJobPostingResponse(
    val role: String,
    val company: String,
    val logo: String?,
    val contents: List<Content>,
) {

    @Serializable
    data class Content(
        val title: String,
        val description: String,
        val url: String,
        val image: String?,
    )
}
