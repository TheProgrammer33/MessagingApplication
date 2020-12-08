//
//  Chat.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/17/20.
//

import Foundation

struct Chat: Codable, Hashable {
    var threadId: Int
    var name: String
    
    init(name: String, id: Int) {
        self.name = name
        self.threadId = id
    }
}
