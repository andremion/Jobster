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

package io.github.andremion.jobster.data.remote

import com.fleeksoft.ksoup.Ksoup
import io.github.andremion.jobster.domain.exception.JobPostingSearchException
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

internal class WebScrapper(
    private val client: HttpClient
) {

    suspend fun scrap(url: String): String =
        try {
            println("Scrapping $url...")
            client.get(url)
                .bodyAsText()
                .let(Ksoup::parse)
                .text()
        } catch (cause: Throwable) {
            throw JobPostingSearchException.General(cause)
        }
}
