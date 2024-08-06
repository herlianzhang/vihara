//
//  APIService.swift
//  App
//
//  Created by Herlian H on 30/03/24.
//

import Foundation
import Moya

protocol DecodeableTargetType: Moya.TargetType {
    associatedtype ResultType: Decodable
}

enum UserAPI: DecodeableTargetType {
    case getUsers

    typealias ResultType = Users
}

enum APIService {
    case getUser
}

extension APIService: TargetType {
    var baseURL: URL { URL(string: "http://192.168.1.5:8080")! }

    var path: String {
        switch self {
        case .getUser:
            return "/users"
        }
    }
    
    var method: Moya.Method {
        switch self {
        case .getUser:
            return .get
        }
    }
    
    var task: Task {
        return .requestPlain
    }
    
    var headers: [String : String]? {
        return ["Content-type": "application/json"]
    }
}
