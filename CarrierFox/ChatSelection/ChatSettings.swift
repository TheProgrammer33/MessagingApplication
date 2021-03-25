//
//  ChatSettings.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 3/9/21.
//

import SwiftUI

struct ChatSettings: View {
    @ObservedObject var userData: UserData = .shared
    @State private var showChatSettings: Bool = false
    var body: some View {
        VStack {
            Button(action: {
                showChatSettings.toggle()
            }) {
                Text(NSLocalizedString("Chat Settings", comment: "Chat Settings"))
            }
        }
        .popover(
            isPresented: self.$showChatSettings,
            arrowEdge: .bottom
        ) {
            VStack {
                Toggle(isOn: $userData.selectedChatMuted) {
                    Text(NSLocalizedString("Mute Chat", comment: "Mute Chat"))
                }.toggleStyle(SwitchToggleStyle())
                Button(action: {
                    muteThread(threadMuted: userData.selectedChatMuted, threadId: userData.selectedChatID)
                }) {
                    Text(NSLocalizedString("Save", comment: "Save Chat Settings"))
                }.padding(.bottom)
                
                Button(action: {
                    clearMessages(threadId: userData.selectedChatID)
                    getMessages(threadID: userData.selectedChatID) { (messages) in
                        if(!messages.isEmpty) {
                            userData.publishMessageChanges(messages: updateMessages(messages))
                        }
                        else {
                            userData.publishMessageChanges(messages: [])
                        }
                    }
                }) {
                    Text(NSLocalizedString("Delete All Messages", comment: "Delete all messages"))
                }.padding(.top)
            }.padding()
        }
        
        
    }
}

struct ChatSettings_Previews: PreviewProvider {
    static var previews: some View {
        ChatSettings()
    }
}
