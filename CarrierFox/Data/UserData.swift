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
    init() {}
    
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
    @Published var selectedChatMuted: Bool = false
    

    func publishMessageChanges(messages: [Message]) {
        var tempMessages:[Message] = []
        for message in messages {
            var tempMessage:Message = message
            tempMessage.updateMessageBody(newMessageBody: decryptCommonCrypto(cyphertext: message.messageBody))
            tempMessages.append(tempMessage)
        }
        DispatchQueue.main.async {
            self.messages = tempMessages
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
