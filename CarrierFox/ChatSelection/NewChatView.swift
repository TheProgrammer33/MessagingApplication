//
//  NewChatView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 1/28/21.
//

import SwiftUI

struct NewChatView: View {
    @State private var searchFriends: String = ""
    @State private var ison:Bool = false
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        VStack {
            TextField("Search Friends", text: $searchFriends).padding()
            VStack {
                ForEach(userData.friendList, id: \.self) { friend in
                    HStack {
                        Toggle(isOn: $ison) {
                            Text(friend.username)
                        }
                        Spacer()
                    }
                }
            }
//            VStack {
//                Text("test")
//                Text("test 2")
//                Toggle(isOn: $ison) {
//                    Text("Demo")
//                }.toggleStyle(CheckboxToggleStyle())
//                Toggle(isOn: $ison) {
//                    Text("Demo")
//                }.toggleStyle(CheckboxToggleStyle())
//                Toggle(isOn: $ison) {
//                    Text("Demo")
//                }.toggleStyle(CheckboxToggleStyle())
//                Toggle(isOn: $ison) {
//                    Text("Demo")
//                }.toggleStyle(CheckboxToggleStyle())
//            }
            Button(action: {
                self.searchFriends = ""
            }){
                Text("Create")
            }
        }.padding()
    }
}

struct NewChatView_Previews: PreviewProvider {
    static var previews: some View {
        NewChatView()
    }
}
