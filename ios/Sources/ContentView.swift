//
//  ContentView.swift
//  App
//
//  Created by Herlian H on 12/01/24.
//

import SwiftUI
import AppAuth

struct ContentView: View {
    @EnvironmentObject var userAuth: UserAuth
    var body: some View {
        if userAuth.isLoggedIn {

            VStack(alignment: .leading, content: {
                HStack {
                    Image("test")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 150)
                        .padding()
                    Spacer()
                }
                .frame(maxWidth: .infinity, alignment: .topLeading)

                TabView {
                    HomeView().tabItem {
                        Label("Home", systemImage: "house")
                    }
                }
            })
        } else {
            LoginView()
        }
    }
}

#Preview {
    ContentView()
}
