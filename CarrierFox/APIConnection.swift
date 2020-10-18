//
//  APIConnection.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/24/20.
//

import Foundation
import CoreLocation

func connectToAPI() -> Void
{
    let url = URL(string: "https://catherinegallaher.com")

    guard url != nil else {
        print("Error creating url object")
        return
    }
    
    // Create URL Request
    var request = URLRequest(url: url!, cachePolicy: .useProtocolCachePolicy, timeoutInterval: 10)
    // Specify HTTP Method to use
    request.httpMethod = "GET"
    // Send HTTP Request
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
            let stringResponse = String(decoding: data, as: UTF8.self)
            print(stringResponse)
            }
    })
    dataTask.resume()
}


func getMessages(completionHandler: @escaping (Data) -> Void){
    let url = URL(string: "https://catherinegallaher.com/api/thread/1000/messages")

    guard url != nil else {
        print("Error creating url object")
        return
    }
    
    // Create URL Request
    var request = URLRequest(url: url!, cachePolicy: .useProtocolCachePolicy, timeoutInterval: 10)
    
    //Header
    //Body
    
    // Specify HTTP Method to use
    request.httpMethod = "GET"
    // Send HTTP Request
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
            let stringResponse = String(decoding: data, as: UTF8.self)
            print(stringResponse)
            completionHandler(data)
            }
    })
    dataTask.resume()
}


func sendMessage(myMessage: String){
    //var messages: [Message]
    //var myMessage: String
    //myMessage = "Thisisatestwithoutspaces"
    let urlString = "https://catherinegallaher.com/api/thread/1000/message/add?message="+myMessage
    print(urlString)
    let url = URL(string: urlString)

    guard url != nil else {
        print("Error creating url object")
        return
    }
    
    // Create URL Request
    var request = URLRequest(url: url!, cachePolicy: .useProtocolCachePolicy, timeoutInterval: 10)
    
    //Header
    //Body
    
    // Specify HTTP Method to use
    request.httpMethod = "POST"
    // Send HTTP Request
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
            let stringData = String(decoding: data, as: UTF8.self)
            print(stringData)
        }
    })
    dataTask.resume()
}
