//
//  FriendsAPIConnection.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/23/20.
//

import Foundation
import CoreLocation

func addFriend(completionHandler: @escaping (Bool) -> Void, friendUsername: String, sessionID: String){
    let urlString = "https://catherinegallaher.com/api/add-friend"
    let url = URL(string: urlString)

    guard url != nil else {
        print("Error creating url object")
        return
    }
    
    var request = URLRequest(url: url!, cachePolicy: .useProtocolCachePolicy, timeoutInterval: 10)

    let headers = [
        "Content-Type": "application/x-www-form-urlencoded"
    ]
    request.allHTTPHeaderFields = headers
    let requestBody = "username=\(friendUsername)&sessionId=\(sessionID)"
    request.httpBody = requestBody.data(using: String.Encoding.utf8)
    request.httpMethod = "POST"
    
    let session = URLSession.shared
    
    let dataTask = session.dataTask(with: request, completionHandler: { (data, response, error) in
        if let error = error {
                print("Error with sending data: \(error)")
                return
        }
              
        guard let httpResponse = response as? HTTPURLResponse,
            (200...299).contains(httpResponse.statusCode) else {
            print("Error with the response, unexpected status code: \(String(describing: response))")
            return
        }

        if let data = data {
            let stringData = String(decoding: data, as: UTF8.self)
            print(stringData)
            if (stringData == "{}") {
                completionHandler(true)
            }
            else {
                completionHandler(false)
            }
        }
    })
    dataTask.resume()
}

func deleteFriend(friendUsername: String, sessionID: String, completionHandler: @escaping (Bool) -> Void){
    
    let urlString = "https://catherinegallaher.com/api/remove-friend"
    let url = URL(string: urlString)

    guard url != nil else {
        print("Error creating url object")
        return
    }
    
    var request = URLRequest(url: url!, cachePolicy: .useProtocolCachePolicy, timeoutInterval: 10)

    let headers = [
        "Content-Type": "application/x-www-form-urlencoded"
    ]
    request.allHTTPHeaderFields = headers
    let requestBody = "username=\(friendUsername)&sessionId=\(sessionID)"
    request.httpBody = requestBody.data(using: String.Encoding.utf8)
    request.httpMethod = "PUT"
    
    let session = URLSession.shared
    
    let dataTask = session.dataTask(with: request, completionHandler: { (data, response, error) in
        if let error = error {
                print("Error with sending data: \(error)")
                return
        }
              
        guard let httpResponse = response as? HTTPURLResponse,
            (200...299).contains(httpResponse.statusCode) else {
            print("Error with the response, unexpected status code: \(String(describing: response))")
            return
        }

        if let data = data {
            let stringData = String(decoding: data, as: UTF8.self)
            print(stringData)
            if (stringData == "{}") {
                completionHandler(true)
            }
            else {
                completionHandler(false)
            }
        }
    })
    dataTask.resume()
}

func getFriends(sessionID: String, completionHandler: @escaping ([Friend]) -> Void){
    let url = URL(string: "https://catherinegallaher.com/api/friends")
    guard url != nil else {
        print("Error creating url object")
        return
    }
    
    var request = URLRequest(url: url!, cachePolicy: .useProtocolCachePolicy, timeoutInterval: 10)

    let headers = [
        "Content-Type": "application/x-www-form-urlencoded"
    ]
    request.allHTTPHeaderFields = headers
    let requestBody = "sessionId=\(sessionID)"
    request.httpBody = requestBody.data(using: String.Encoding.utf8)
    
    request.httpMethod = "POST"

    let session = URLSession.shared
    
    let dataTask = session.dataTask(with: request, completionHandler: { (data, response, error) in
        if let error = error {
                print("Error with fetching data: \(error)")
                return
        }

        guard let httpResponse = response as? HTTPURLResponse,
            (200...299).contains(httpResponse.statusCode) else {
            print("Error with the response, unexpected status code: \(String(describing: response))")
            return
        }

        if let data = data {
            var stringData = String(decoding: data, as: UTF8.self)
            let start = stringData.index(stringData.startIndex, offsetBy: 11)
            let end =  stringData.index(stringData.endIndex, offsetBy: -1)
            let range = start..<end
            stringData = String(stringData[range])
            let parsedData = Data(stringData.utf8)
            let friendList:[Friend] = try! JSONDecoder().decode([Friend].self, from: parsedData)
            completionHandler(friendList)
        }
    })
    dataTask.resume()
}
