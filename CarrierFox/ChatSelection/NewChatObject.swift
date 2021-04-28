//
//  NewChatObject.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 12/8/20.
//

import Foundation

struct NewChatObject: Codable, Hashable {
//    var users: [String]
//    var _id: String
    var threadId: Int
    var name: String
    
    init() {
//        self.users = []
//        self._id = ""
        self.threadId = 0
        self.name = ""
    }
}
