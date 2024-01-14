//
//  AppSettingsReader.swift
//  App
//
//  Created by Herlian H on 13/01/24.
//

import Foundation

struct AppSettingsRoot: Decodable {
    internal let authCredentials: AuthCredentials
}

struct AuthCredentials: Decodable {
    internal let clientId: String
    internal let issuer: String
}

class AppSettingsReader {
    func loadAppSettings() -> AppSettingsRoot {
        let url = Bundle.main.url(forResource: "Vihara", withExtension: "plist")!
        let data = try! Data(contentsOf: url)
        let appSettings = try! PropertyListDecoder().decode(AppSettingsRoot.self, from: data)
        return appSettings
    }
}
