//
//  SelectChatView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/9/20.
//

import SwiftUI

struct SelectChatView: View {
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        List {
            ChatDetailView(chat: Chat(name: "Friend 1", id: 0))
            ChatDetailView(chat: Chat(name: "Friend 1", id: 1))
            ChatDetailView(chat: Chat(name: "Friend 1", id: 2))
        }
        .frame(width: 170.0)
    }
}

struct SelectChatView_Previews: PreviewProvider {
    static var previews: some View {
        SelectChatView()
    }
}
