//
//  ChatSelectionView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/19/20.
//

import SwiftUI

struct ChatSelectionView: View {
    @State private var showFriendSearch = false
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        VStack {
            List {
                HStack {
                    Text("Add Chat").padding([.leading, .trailing])
                        .padding(.top, 2.0)
                    Button(action: {
                        self.showFriendSearch.toggle()
                    }) {
                        Text("+")
                    }.padding([.trailing])
                        .padding(.top, 2.0)
                }
            }.frame(width: 170.0, height: 35.0)
            if(showFriendSearch) {
                NewChatSelectionView()
            }
            SelectChatView()
        }.padding()
    }
}

struct ChatSelectionView_Previews: PreviewProvider {
    static var previews: some View {
        ChatSelectionView()
    }
}
