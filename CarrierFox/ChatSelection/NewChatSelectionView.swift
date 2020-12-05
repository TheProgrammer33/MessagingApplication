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
    
    let handlerBlock: (Bool) -> Void = {
        if $0 {
            
        }
    }
    
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
                newChat(completionHandler: handlerBlock, friendUsername: menuText, sessionID: userData.sessionID, currentUser: userData.username)
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
