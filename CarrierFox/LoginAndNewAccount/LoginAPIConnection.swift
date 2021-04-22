//
//  LoginAPIConnection.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 10/20/20.
//

import Foundation
import CoreLocation

func createNewAccount(email: String, username: String, password: String, completionHandler: @escaping (Bool) -> Void){
    let urlString = "https://catherinegallaher.com/api/create-account"
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
    let requestBody = "email=\(email)&username=\(username)&password=\(password)"
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

func login(username: String, password: String, completionHandler: @escaping (LoginObject) -> Void) {
    
    let urlString = "https://catherinegallaher.com/api/login"
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
    let requestBody = "threadID=1000&username=\(username)&password=\(password)"
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
            print(stringData.split(separator: "\"")[1])
            if (stringData.split(separator: "\"")[1] != "userMessage") {
                let sessionID = stringData.split(separator: "\"")[3]
                print(sessionID)
                let loginResponse:LoginObject = try! JSONDecoder().decode(LoginObject.self, from: data)
                completionHandler(loginResponse)
            }
            else {
                completionHandler(LoginObject())
            }
        }
    })
    dataTask.resume()
}

