import SwiftUI
import GoogleSignIn

@main
struct BazelApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    var userAuth = UserAuth()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(userAuth)
                .onAppear {
                    GIDSignIn.sharedInstance.restorePreviousSignIn { user, error in
                        print("Masuk user: \(user)")
                        print("Masuk error: \(error)")
                    }
                }
                .onOpenURL { url in
                    GIDSignIn.sharedInstance.handle(url)
                }
        }
    }
}
