//
//  Message.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/28/20.
//

import Foundation

struct Message: Codable, Hashable {
    var _id: String
    var user: String
    var messageBody: String
    var messageSentDate: Date
    var isEdited: Bool
    
    
    init(messageBody: String, messageSentDate: Date) {
        self._id = "0"
        self.messageBody = messageBody
        self.user = ""
        self.messageSentDate = messageSentDate
        self.isEdited = false
    }
    
    mutating func updatedEdited() {
        self.isEdited = true
    }
}