//
//  LoginObject.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 12/5/20.
//

import Foundation


struct LoginObject: Codable, Hashable {
    let _id: String
    let email: String
    let username: String
    let friends: [Friend]
    let notifications: Bool
    let threads: [Chat]
    
    init() {
        self._id = ""
        self.email = ""
        self.username = ""
        self.friends = []
        self.notifications = false
        self.threads = []
    }
}
