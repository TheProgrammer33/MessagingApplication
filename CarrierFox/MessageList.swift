//
//  MessageHistoryDetail.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/28/20.
//

import SwiftUI

struct MessageList: View {
    @EnvironmentObject var userData: UserData
    var body: some View {
        List {
            ForEach(userData.messages, id: \.self) {message in
                MessageRow(message: message)
            }
        }
    }
}

struct MessageList_Previews: PreviewProvider {
    static var previews: some View {
        MessageList().environmentObject(UserData())
    }
}
