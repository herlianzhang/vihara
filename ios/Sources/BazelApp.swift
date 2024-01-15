import SwiftUI

@main
struct BazelApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    var userAuth = UserAuth()

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(userAuth)
        }
    }
}
