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
        let handlerBlock: (Bool) -> Void = {_ in
            print("Friend Deleted")
        }
        return HStack {
            Text(friend.username)
            Spacer()
            Button(action: {
                print("Deleting friend")
                deleteFriend(friendUsername: friend.username, sessionID: userData.sessionID, completionHandler: handlerBlock)
            }) {
                Text("Delete")
            }
        }.padding()
    }
}

struct FriendDetailView_Previews: PreviewProvider {
    static var previews: some View {
        FriendDetailView(friend: Friend(friend: "Demo Friend")).environmentObject(UserData())
    }
}
