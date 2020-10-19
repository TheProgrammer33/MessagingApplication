//
//  Message.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/28/20.
//

import Foundation

struct Message: Codable, Hashable {
    var id: Int
    var user: String
    var messageBody: String
    //var messageSendDate: Date
    //var isEdited: Bool
    
    
    init(message: String) {//messageSendDate: Date) {
        self.id = 0
        self.messageBody = message
        self.user = "Who cares"
        //self.messageSendDate = messageSendDate
        //self.isEdited = false
    }
    
//    mutating func updatedEdited() {
//        self.isEdited = true
//    }
}
