//
//  FriendWithSelect.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 2/4/21.
//

import Foundation

struct FriendWithSelect: Codable, Hashable {
    var username: String
    var selected: Bool
    
    
    init(friend: String) {
        self.username = friend
        selected = false
    }
    
    mutating func select() {
        self.selected = true
    }
    
    mutating func reset() {
        self.selected = false
    }
    
}
