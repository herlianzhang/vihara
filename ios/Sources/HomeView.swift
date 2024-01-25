//
//  HomeView.swift
//  App
//
//  Created by Herlian H on 13/01/24.
//

import SwiftUI
import SwiftProtobuf
import GRPC
import NIOCore
import NIOPosix
import proto_servicea_proto
import proto_swift_servicea

struct HomeView: View {
    @EnvironmentObject var userAuth: UserAuth
    var body: some View {
        VStack {
            Text("Welcome \(userAuth.given_name) \(userAuth.family_name)")
                .padding(.bottom, 20).font(.headline)
            Button("Log out") {
                userAuth.logout()
            }
            Button("GRPC Call") {
                try! grpcCall()
            }
        }
    }

    func grpcCall() throws {
        let group = MultiThreadedEventLoopGroup(numberOfThreads: 1)
        let channel = try GRPCChannelPool.with(
            target: .host("localhost", port: 42042),
            transportSecurity: .plaintext,
            eventLoopGroup: group
        )
        let client = Servicea_ServiceANIOClient(channel: channel)

        let request = Servicea_SumRequest.with {
            $0.a = 5
            $0.b = 5
        }

        let cancel = client.sumStream(request) { response in
            print(response)
        }

        DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(6)) {
            cancel.cancel(promise: .none)
        }
    }
}
