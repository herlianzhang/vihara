//
//  LoginView.swift
//  App
//
//  Created by Herlian H on 13/01/24.
//

import SwiftUI
import GoogleSignIn
import Moya

struct User: Decodable {
    let user_id: String
    let username: String
    let google_id: String?
    let apple_id: String?
    let email: String?
    let avatar: String?
}

struct LoginView: View {
    let provider = MoyaProvider<APIService>(plugins: [NetworkLoggerPlugin()])
    @EnvironmentObject var userAuth: UserAuth
    var body: some View {
        VStack {
            //            Image("test")
            //                .resizable()
            //                .aspectRatio(contentMode: .fit)

            Text("Welcome to test")
            Button("Login") {
                getsomething()
//                signIn()
//                userAuth.login()
            }
        }
        .padding()
    }

    func getsomething() {
        provider.request(.getUser) { result in
            switch result {
            case .success(let response):
                guard let users = try? JSONDecoder().decode([User].self, from: response.data) else { return }
                print("Masuk pak eko \(users)")
            case .failure(let error):
                print("Masuk error \(error)")
            }
        }
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
