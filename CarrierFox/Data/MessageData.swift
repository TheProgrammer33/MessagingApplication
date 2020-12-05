//
//  MessageData.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 10/17/20.
//

import Foundation

var messageData: [Message] = []


func updateMessages<T: Decodable>(_ jsonData: Data)->T{
    let data: Data
    data = jsonData
    
    do {
        let decoder = JSONDecoder()
        decoder.dateDecodingStrategy = .formatted(DateFormatter.longDateTime)
        return try decoder.decode(T.self, from: data)
    } catch {
        fatalError("Couldn't parse data as \(T.self):\n\(error)")
    }
}

extension DateFormatter {
    static let longDateTime: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        formatter.calendar = Calendar(identifier: .iso8601)
        formatter.timeZone = TimeZone(secondsFromGMT: 0)
        formatter.locale = Locale(identifier: "en_US_POSIX")
        return formatter
    }()
}
