//
//  Friend.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/24/20.
//

import Foundation

struct Friend: Codable, Hashable {
    var _id: String
    var username: String
    
    
    init(friend: String) {
        self._id = "0"
        self.username = friend
    }
    
}
