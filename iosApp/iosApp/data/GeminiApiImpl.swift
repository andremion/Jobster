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
