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
    init() {
        print("in userdata init")
    }
    
    static let shared = UserData()
    
    @Published var messages = messageData
    @Published var username = ""
    @Published var chatList: [Chat] = []
    @Published var selectedChatID = 0
    @Published var selectedChatName = ""
    @Published var sessionID = ""
    @Published var friendList: [Friend] = []
    @Published var notificationsIsOn: Bool = false
    @Published var scrollIndex: Binding<CGPoint?>? = nil //Binding<CGPoint?>?(CGPoint(x: 0, y: 10000))
    @Published var newChatSelection: [FriendWithSelect] = []
    

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
    
    func publishNotificationChanges(notifications: Bool) {
        DispatchQueue.main.async {
            self.notificationsIsOn = notifications
        }
    }
    
    func publishChatChanges(chats: [Chat]) {
        DispatchQueue.main.async {
            self.chatList = chats
            if(!chats.isEmpty) {
                self.publishSelectedChatChanges(chat: chats[0])
            }
        }
    }
    
    func publishSelectedChatChanges(chat: Chat) {
        DispatchQueue.main.async {
            self.selectedChatName = chat.name
            self.selectedChatID = chat.threadId
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
            self.newChatSelection = []
            for friend in friendList {
                //self.newChatSelection.updateValue(false, forKey: friend.username)
                self.newChatSelection.append(FriendWithSelect(friend: friend.username))
            }
        }
    }

    
    func publishScrollIndexChanges(scrollTo: CGPoint) {
        DispatchQueue.main.async {
            self.scrollIndex?.wrappedValue = scrollTo
        }
    }
    
    func updateScrollIndex() {
        DispatchQueue.main.async {
            self.scrollIndex?.wrappedValue = CGPoint(x:0, y: 30*self.messages.count)
        }
    }
}
