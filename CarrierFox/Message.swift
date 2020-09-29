//
//  Message.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/28/20.
//

import Foundation

struct Message {
    var messageBody: String
    var messageSendDate: Date
    var edited: Bool
    
    init(messageBody: String, messageSendDate: Date) {
        self.messageBody = messageBody
        self.messageSendDate = messageSendDate
        self.edited = false
    }
    
    mutating func updatedEdited() {
        self.edited = true
    }
}
