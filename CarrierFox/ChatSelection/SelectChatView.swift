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
            ForEach(userData.chatList, id: \.self) { chat in
                ChatDetailView(chat: chat)
            }
        }
        .frame(width: 170.0)
    }
}

struct SelectChatView_Previews: PreviewProvider {
    static var previews: some View {
        SelectChatView()
    }
}
