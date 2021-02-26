//
//  FriendDetailView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/22/20.
//

import SwiftUI

struct FriendDetailView: View {
    var friend: Friend
    @ObservedObject var userData: UserData = .shared
    
    var body: some View {
        return HStack {
            Text(friend.username)
            Spacer()
            Button(action: {
                print("Deleting friend")
                deleteFriend(friendUsername: friend.username, sessionID: userData.sessionID){ (result) in
                    getFriends(sessionID: userData.sessionID) { (friends) in
                        userData.publishFriendListChanges(friendList: friends)
                    }
                }
            }) {
                Text(NSLocalizedString("Delete", comment: "Delete Friend"))
            }
        }.padding()
    }
}

struct FriendDetailView_Previews: PreviewProvider {
    static var previews: some View {
        FriendDetailView(friend: Friend(friend: "Demo Friend")).environmentObject(UserData())
    }
}
