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

package io.github.andremion.jobster.data.remote.api

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ServerException
import io.github.andremion.jobster.data.BuildConfig
import io.github.andremion.jobster.data.remote.model.GetJobPostingResponse
import io.github.andremion.jobster.domain.exception.JobPostingSearchException

internal class GeminiApiImpl : GeminiApi() {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GeminiApiKey
    )

    override suspend fun getJobPosting(content: String): GetJobPostingResponse =
        try {
            getResponse(content) { prompt ->
                generativeModel.generateContent(prompt)
                    .text.toString()
            }
        } catch (cause: ServerException) {
            throw JobPostingSearchException.Server(cause.message!!, cause)
        } catch (cause: Throwable) {
            throw JobPostingSearchException.General(cause)
        }
}
