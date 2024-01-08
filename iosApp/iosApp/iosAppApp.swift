import SwiftUI
import SharedUI

@main
struct iosAppApp: App {
    init() {
        DIKt.doInitDI()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
