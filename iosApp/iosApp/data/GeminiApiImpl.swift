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

import SharedUI
import GoogleGenerativeAI

private class GetResponseStringFunction : KotlinSuspendFunction1 {

    private let generativeModel = GenerativeModel(
        name: "gemini-pro",
        apiKey: GeminiApiKey.default
    )

    func invoke(p1: Any?) async throws -> Any? {
        let response = try await generativeModel.generateContent(p1 as! String)
        return response.text ?? ""
    }
}

class GeminiApiImpl: DataGeminiApi {

    override func getJobPosting(content: String) async throws -> DataGetJobPostingResponse {
        do {
            return try getResponse(
                content: content,
                getResponseString: GetResponseStringFunction()
            )
        } catch let error as GenerateContentError {
            throw DomainJobPostingSearchException.Server(
                message: error.localizedDescription,
                cause: error as? KotlinThrowable
            ).asError()
        } catch {
            throw DomainJobPostingSearchException.General(
                cause: error as? KotlinThrowable
            ).asError()
        }
    }
}
