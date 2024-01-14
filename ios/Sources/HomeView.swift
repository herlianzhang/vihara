//
//  HomeView.swift
//  App
//
//  Created by Herlian H on 13/01/24.
//

import SwiftUI

struct HomeView: View {
    @EnvironmentObject var userAuth: UserAuth
    var body: some View {
        VStack {
            Text("Welcome \(userAuth.given_name) \(userAuth.family_name)")
                .padding(.bottom, 20).font(.headline)
            Button("Log out") {
                userAuth.logout()
            }
        }
    }
}
