//
//  NewChatSelectionView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/10/20.
//

import SwiftUI

struct NewChatSelectionView: View {
    @ObservedObject var userData: UserData = .shared
    @State var menuText = "Select a Contact"
    
    var body: some View {
        VStack {
            MenuButton(label: Text(menuText)) {
                ForEach(userData.friendList, id: \.self) { friend in
                    Button(action: {
                        menuText = friend.username
                    }) {
                        Text(friend.username)
                    }
                }
            }
            Button(action: {
                if(menuText != "Select a Contact") {
                    newChat(friendUsername: menuText, sessionID: userData.sessionID, currentUser: userData.username) { (responseObject) in
                        var tempChatList = userData.chatList
                        tempChatList.append(Chat(name: responseObject.name, id: responseObject.threadId))
                        userData.publishChatChanges(chats: tempChatList)
                    }
                }
            }) {
                Text("Submit")
            }
        }.padding([.bottom]).frame(width: 170).grayscale(0.90)
    }
}

struct NewChatSelectionView_Previews: PreviewProvider {
    static var previews: some View {
        NewChatSelectionView()
    }
}
