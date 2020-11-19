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
    @Published var messages = messageData
    @Published var username = ""
    @Published var chatList: [Chat] = []
    @Published var selectedChatID = 0
    @Published var selectedChatName = ""
    @Published var sessionID = ""
    

    func publishMessageChanges(messages: [Message]) {
        print("In publishMessageChanges")
        DispatchQueue.main.async {
            print("In dispatchQueue")
            print(messages)
            self.messages = messages
            print("The messages: ")
            print(self.messages)
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
        DispatchQueue.main.async {
            self.sessionID = id
        }
    }
}
