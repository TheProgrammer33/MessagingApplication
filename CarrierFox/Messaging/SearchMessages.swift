//
//  SearchMessages.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 3/10/21.
//

import SwiftUI

struct SearchMessages: View {
    @ObservedObject var userData: UserData = .shared
    @State private var showMessageSearch: Bool = false
    @State private var searchMessages:String = ""
    @State private var returnedMessages: [String] = []
    var body: some View {
        Button(action: {
            showMessageSearch.toggle()
        }) {
            Text("Search Messages")
        }.popover(
            isPresented: self.$showMessageSearch,
            arrowEdge: .bottom
        ) {
            VStack {
                HStack {
                    TextField(NSLocalizedString("Search Messages", comment: "Search Messages"), text: $searchMessages).padding()
                    Button(action: {
                        returnedMessages = CarrierFox.searchMessages(toSearch: searchMessages, threadId: userData.selectedChatID)
                    }) {
                        Text(NSLocalizedString("Search", comment: "Search messages"))
                    }
                }
                ForEach(returnedMessages, id: \.self) { message in
                    Text(message)
                }
            }.padding()
        }
    }
}

struct SearchMessages_Previews: PreviewProvider {
    static var previews: some View {
        SearchMessages()
    }
}
