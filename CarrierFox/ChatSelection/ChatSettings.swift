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
                Text("Chat Settings")
            }
        }
        .popover(
            isPresented: self.$showChatSettings,
            arrowEdge: .top
        ) {
            VStack {
                Toggle(isOn: $userData.notificationsIsOn) {
                    Text("Mute Chat")
                }.toggleStyle(SwitchToggleStyle())
                Button(action: {
                    
                }) {
                    Text("Delete all messages")
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
