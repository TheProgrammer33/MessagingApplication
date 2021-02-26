//
//  NewChatView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 1/28/21.
//

import SwiftUI

func search(searchVal: String, friendList: [FriendWithSelect]) -> [FriendWithSelect] {
    if (searchVal != "") {
        var filteredSearch: [FriendWithSelect] = []
        filteredSearch = friendList.filter { friend in
            return friend.username.lowercased().contains(searchVal.lowercased())
        }
        return filteredSearch
    }
    else {
        return friendList
    }
    
}

struct NewChatView: View {
    @State private var searchFriends: String = ""
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        VStack {
            TextField(NSLocalizedString("Search Friends", comment: "Search Friends List"), text: $searchFriends).padding()
            VStack {
                ForEach(0..<userData.newChatSelection.count) { i in
                    HStack {
                        if(search(searchVal: searchFriends, friendList: userData.newChatSelection).contains(userData.newChatSelection[i])) {
                            Toggle("\(userData.newChatSelection[i].username)", isOn: self.$userData.newChatSelection[i].selected)
                            Spacer()
                        }
                    }
                }
            }
            Button(action: {
                var selectedFriends: [String] = []
                for friend in userData.newChatSelection {
                    if(friend.selected == true)
                    {
                        selectedFriends.append(friend.username)
                    }
                }
                newChat(friendUsernames: selectedFriends, sessionID: userData.sessionID, currentUser: userData.username) { (responseObject) in
                    var tempChatList = userData.chatList
                    tempChatList.append(Chat(name: responseObject.name, id: responseObject.threadId))
                    userData.publishChatChanges(chats: tempChatList)
                }
                self.searchFriends = ""
            }){
                Text(NSLocalizedString("Create", comment: "Create new chat"))
            }
        }.padding()
    }
}

struct NewChatView_Previews: PreviewProvider {
    static var previews: some View {
        NewChatView()
    }
}
