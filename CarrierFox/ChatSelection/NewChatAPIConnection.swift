//
//  NewChatAPIConnection.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/25/20.
//

import Foundation
import CoreLocation

func newChat(completionHandler: @escaping (Bool) -> Void, friendUsername: String, sessionID: String, currentUser: String){
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
    let requestBody = "users=\(friendUsername),\(currentUser)"
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
