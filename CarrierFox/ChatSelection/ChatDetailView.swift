//
//  ChatDetailView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/9/20.
//

import SwiftUI

struct ChatDetailView: View {
    var chat: Chat
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        VStack {
            Button(action: {
                print(chat)
                userData.publishSelectedChatChanges(chat: chat)
                print(userData.selectedChatID)
                getMessages(threadID: userData.selectedChatID) { (messages) in
                    if(!messages.isEmpty)
                    {
                        userData.publishMessageChanges(messages: updateMessages(messages))
                    }
                    else
                    {
                        userData.publishMessageChanges(messages: [])
                    }
                }
            }) {
                Text(chat.name)
                    .padding()
                    .opacity(100)
                    .frame(width: 150, height: 55.0)
                    .fixedSize(horizontal: false, vertical: true)
            }
            .buttonStyle(BorderlessButtonStyle())
            Divider()
        }.frame(width: 150.0, height: 60.0)
    }
}

struct ChatDetailView_Previews: PreviewProvider {
    static var previews: some View {
        ChatDetailView(chat: Chat(name: "Demo Name", id: 1)).environmentObject(UserData())
    }
}

