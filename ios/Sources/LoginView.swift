//
//  LoginView.swift
//  App
//
//  Created by Herlian H on 13/01/24.
//

import SwiftUI
import AppAuth

struct LoginView: View {
    @EnvironmentObject var userAuth: UserAuth
    var body: some View {
        VStack {
//            Image("test")
//                .resizable()
//                .aspectRatio(contentMode: .fit)

            Text("Welcome to test")
            Button("Login") {
                userAuth.login()
            }
        }
        .padding()
    }
}
