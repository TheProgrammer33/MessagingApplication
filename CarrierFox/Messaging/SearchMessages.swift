//
//  SearchMessages.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 3/10/21.
//

import SwiftUI
func searchMessages(searchValue: String, listToSearch: [Message]) -> [Message] {
    let result = listToSearch.filter { message in
        return message.messageBody.lowercased().contains(searchValue.lowercased())
    }
    return result
}

struct SearchMessages: View {
    @ObservedObject var userData: UserData = .shared
    @State private var showMessageSearch: Bool = false
    @State private var searchMessages:String = ""
    @State private var returnedMessages: [Message] = []
    var body: some View {
        Button(action: {
            showMessageSearch.toggle()
        }) {
            Image("Search")
        }.popover(
            isPresented: self.$showMessageSearch,
            arrowEdge: .bottom
        ) {
            VStack {
                HStack {
                    TextField(NSLocalizedString("Search Messages", comment: "Search Messages"), text: $searchMessages).padding()
                    Button(action: {
                        //returnedMessages = CarrierFox.searchMessages(toSearch: searchMessages, threadId: userData.selectedChatID)
                        self.returnedMessages = CarrierFox.searchMessages(searchValue: self.searchMessages, listToSearch: userData.messages)
                    }) {
                        Text(NSLocalizedString("Search", comment: "Search messages"))
                    }
                }
                ForEach(returnedMessages, id: \.self) { message in
                    HStack {
                        Text(message.user + ": ").bold()
                        Text(message.messageBody).padding(.vertical)
                    }
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
