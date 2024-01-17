import SwiftUI
import SharedUI

@main
struct iosAppApp: App {
    init() {
        DIKt.doInitDI(geminiApi: GeminiApiImpl())
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
