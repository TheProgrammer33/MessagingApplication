//
//  MessageRow.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/28/20.
//

import SwiftUI

struct MessageRow: View {
    var message: Message
    var body: some View {
        let formatter = DateFormatter()
        formatter.dateFormat = "MM/dd/yyyy h:m a"
        let date = formatter.string(from: message.messageSendDate)
        return HStack {
            Text(message.messageBody).padding(.horizontal)
            Spacer()
            Text(date).padding(.horizontal)
        }
    }
}

struct MessageRow_Previews: PreviewProvider {
    static var previews: some View {
        MessageRow(message: Message(messageBody: "Message Preview", messageSendDate: Date()))
    }
}
