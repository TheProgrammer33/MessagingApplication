//
//  FriendsView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/22/20.
//

import SwiftUI

struct FriendsView: View {
    @ObservedObject var userData: UserData = .shared
    @State private var isNewFriend: Bool = false
    @State private var searchFriend = ""
    @State private var successfulAddFriend = false
    
    var body: some View {
        return VStack {
            HStack {
                Text(NSLocalizedString("Friends", comment: "Friends"))
                    .font(.title)
                Spacer()
            }.padding(.horizontal)
            List {
                ForEach(userData.friendList, id: \.self) { friend in
                    FriendDetailView(friend: friend)
                    
                }
            }
            HStack {
                Button(action: {
                    self.isNewFriend.toggle()
                }) {
                    Text(NSLocalizedString("+ Add Friend", comment: "Add Friend"))
                }
                Spacer()
            }.padding()
            
            if(isNewFriend) {
                VStack {
                    HStack {
                        TextField(NSLocalizedString("Search For New Friend", comment: "Search For New Friend"), text: $searchFriend)
                        Spacer()
                        Button(action: {
                            print(userData.sessionID)
                            if(searchFriend != userData.username) {
                                addFriend(friendUsername: searchFriend, sessionID: userData.sessionID) { (result) in
                                    if (result == true){
                                        self.successfulAddFriend = true
                                        getFriends(sessionID: userData.sessionID) {(friends) in
                                            userData.publishFriendListChanges(friendList: friends)
                                        }
                                        searchFriend = ""
                                    }
                                    else {
                                        self.successfulAddFriend = false
                                    }
                                }
                            }
                        }) {
                            Text(NSLocalizedString("Add", comment: "Add Friend"))
                        }
                        
                    }
                    if(!successfulAddFriend) {
                        Text(NSLocalizedString("Not a valid username", comment: "Not a valid username"))
                            .foregroundColor(Color.red)
                    }
                }.padding([.leading, .bottom, .trailing])
            }
        }
    }
}

struct FriendsView_Previews: PreviewProvider {
    static var previews: some View {
        FriendsView()
    }
}
