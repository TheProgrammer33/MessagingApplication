//
//  Logout.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 12/10/20.
//

import SwiftUI

struct Logout: View {
    @ObservedObject var userData: UserData = .shared
    let myWindow:NSWindow?
    var body: some View {
        VStack {
            Text("Logout")
                .font(.title).padding()
            Button(action: {
                userData.publishMessageChanges(messages: [])
                userData.publishSelectedChatChanges(chat: Chat(name: "", id: 0))
                showLoginWindow()
                self.myWindow?.close()
            }) {
                Text("Logout")
            }
        }.padding()
    }
}

struct Logout_Previews: PreviewProvider {
    static var previews: some View {
        Logout(myWindow: nil)
    }
}
