//
//  SettingsAPIConnect.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/23/20.
//

import Foundation
import CoreLocation

func getSettings(completionHandler: @escaping (Bool) -> Void){
    let urlString = "https://catherinegallaher.com/api/"
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
    let requestBody = ""
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

func saveNotifications(sessionID: String, notifications: Bool){
    
    let urlString = "https://catherinegallaher.com/api/settings/save"
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
    let requestBody = "sessionId=\(sessionID)&notifications=\(notifications)"
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

        if data != nil {
            return
        }
    })
    dataTask.resume()
}


func changePassword(sessionID: String, password: String, oldPassword: String, completionHandler: @escaping (Bool) -> Void){
    let urlString = "https://catherinegallaher.com/api/change-password"
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
    let requestBody = "sessionId=\(sessionID)&password=\(oldPassword)&newPassword=\(password)"
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

func changeEmail(sessionID: String, newEmail: String, completionHandler: @escaping (Bool) -> Void){
    let urlString = "https://catherinegallaher.com/api/change-email"
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
    let requestBody = "sessionId=\(sessionID)&newEmail=\(newEmail)"
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
