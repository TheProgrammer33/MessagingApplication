//
//  MessageAPIConnect.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/24/20.
//

import Foundation
import CoreLocation

func getMessages(threadID:Int, completionHandler: @escaping (Data) -> Void){
    let url = URL(string: "https://catherinegallaher.com/api/thread/\(threadID)/messages")
    guard url != nil else {
        print("Error creating url object")
        return
    }
    
    var request = URLRequest(url: url!, cachePolicy: .useProtocolCachePolicy, timeoutInterval: 10)
    request.httpMethod = "GET"

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
            completionHandler(data)
        }
    })
    dataTask.resume()
}

func sendMessage(myMessage: String){
    let url = URL(string: "https://catherinegallaher.com/api/thread/1/message/add")
    guard url != nil else {
        print("Error creating url object")
        return
    }
    
    var request = URLRequest(url: url!, cachePolicy: .useProtocolCachePolicy, timeoutInterval: 10)
    let headers = [ "Content-Type": "application/x-www-form-urlencoded" ]
    request.allHTTPHeaderFields = headers
    let requestBody = "threadID=1&message=\(myMessage)&user=MoreCoffee"
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
