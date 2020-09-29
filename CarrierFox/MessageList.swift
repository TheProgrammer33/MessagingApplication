//
//  MessageHistoryDetail.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/28/20.
//

import SwiftUI

struct MessageList: View {
    var messageList: [MessageRow]
    var body: some View {
        List() {
            messageList[0]
            messageList[1]
        }
    }
}

struct MessageList_Previews: PreviewProvider {
    static var previews: some View {
        MessageList(messageList: [MessageRow(message: Message(messageBody: "Message Demo 1", messageSendDate: Date())), MessageRow(message: Message(messageBody: "Message Demo 2", messageSendDate: Date()))])
    }
}
