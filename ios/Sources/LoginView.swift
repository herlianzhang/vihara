//
//  LoginView.swift
//  App
//
//  Created by Herlian H on 13/01/24.
//

import SwiftUI
import GoogleSignIn

struct LoginView: View {
    @EnvironmentObject var userAuth: UserAuth
    var body: some View {
        VStack {
            //            Image("test")
            //                .resizable()
            //                .aspectRatio(contentMode: .fit)

            Text("Welcome to test")
            Button("Login") {
                signIn()
//                userAuth.login()
            }
        }
        .padding()
    }

    func signIn() {
        guard let rootViewController = UIApplication.shared.windows.first?.rootViewController else {
            print("There is no root view controller!")
            return
        }

        GIDSignIn.sharedInstance.signIn(withPresenting: rootViewController) { signInResult, error in
            guard let signInResult = signInResult else {
                print("Error! \(String(describing: error))")
                return
            }
            print("masuk idToken = \(signInResult.user.idToken?.tokenString)")
//            self.authViewModel.state = .signedIn(signInResult.user)
        }
    }
}
