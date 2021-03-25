//
//  NewChatAPIConnection.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/25/20.
//

import Foundation
import CoreLocation

func newChat(friendUsernames: [String], sessionID: String, currentUser: String, completionHandler: @escaping (NewChatObject) -> Void){
    let urlString = "https://catherinegallaher.com/api/create-thread"
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
    var requestBody = "users="
    for friend in friendUsernames {
        requestBody += "\(friend),"
    }
    requestBody += "\(currentUser)"
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
            var stringData = String(decoding: data, as: UTF8.self)
            let start = stringData.index(stringData.startIndex, offsetBy: 1)
            let end =  stringData.index(stringData.endIndex, offsetBy: -1)
            let range = start..<end
            stringData = String(stringData[range])
            print(stringData)
            let parsedData = Data(stringData.utf8)
            let loginResponse:NewChatObject = try! JSONDecoder().decode(NewChatObject.self, from: parsedData)
            completionHandler(loginResponse)
        }
    })
    dataTask.resume()
}

func muteThread(threadMuted: Bool, threadId: Int) {
    let urlString = "https://catherinegallaher.com/api/thread/settings"
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
    let requestBody = "threadMuted=\(threadMuted)&threadId=\(threadId)"
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
        }
    })
    dataTask.resume()
}

func clearMessages(threadId: Int) {
    let urlString = "https://catherinegallaher.com/api/thread/clear-messages"
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
    let requestBody = "threadId=\(threadId)"
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
        }
    })
    dataTask.resume()
}

