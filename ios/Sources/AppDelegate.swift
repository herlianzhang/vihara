//
//  AppDelegate.swift
//  App
//
//  Created by Herlian H on 10/01/24.
//

import AppAuth
import GoogleSignIn

class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    var currentAuthorizationFlow: OIDExternalUserAgentSession?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        print("Your code here")
        return true
    }

    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        // Sends the URL to the current authorization flow (if any) which will
        // process it if it relates to an authorization response.
        if let authorizationFlow = self.currentAuthorizationFlow,
                                 authorizationFlow.resumeExternalUserAgentFlow(with: url) {
            self.currentAuthorizationFlow = nil
            return true
        }

        // Your additional URL handling (if any)

        return false
    }
}
