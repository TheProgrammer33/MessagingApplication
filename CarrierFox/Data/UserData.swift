//
//  UserData.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 10/17/20.
//

import Foundation

import SwiftUI
import Combine

final class UserData: ObservableObject {
    init() { }
    
    static let shared = UserData()
    
    @Published var messages = messageData
    @Published var username = ""
    @Published var chatList: [Chat] = []
    @Published var selectedChatID = 0
    @Published var selectedChatName = ""
    @Published var sessionID = ""
    @Published var friendList: [Friend] = []
    @Published var notificationsIsOn: Bool = false
    

    func publishMessageChanges(messages: [Message]) {
        print("In publishMessageChanges")
        DispatchQueue.main.async {
            self.messages = messages
        }
    }
    
    func publishUsernameChanges(username: String) {
        DispatchQueue.main.async {
            self.username = username
        }
    }
    
    func publishChatChanges(chats: [Chat]) {
        DispatchQueue.main.async {
            self.chatList = chats
        }
    }
    
    func publishSelectedChatChanges(chat: Chat) {
        DispatchQueue.main.async {
            self.selectedChatName = chat.name
            self.selectedChatID = chat.id
        }
    }
    
    func publishSessionIDChange(id: String) {
        print("in publish session id")
        print(id)
        DispatchQueue.main.async {
            self.sessionID = id
        }
    }
    
    func publishFriendListChanges(friendList: [Friend]) {
        DispatchQueue.main.async {
            self.friendList = friendList
        }
    }
}
