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
    
    init(messageBody: String, messageSentDate: Date, _id: String, user:String, isEdited: Bool) {
        self._id = _id
        self.messageBody = messageBody
        self.user = user
        self.messageSentDate = messageSentDate
        self.isEdited = isEdited
    }
    
    mutating func updatedEdited() {
        self.isEdited = true
    }
    
    mutating func updateMessageBody(newMessageBody: String) {
        self.messageBody = newMessageBody
    }
}
