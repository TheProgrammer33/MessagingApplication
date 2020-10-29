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
        let date = formatter.string(from: message.messageSentDate)
        return VStack {
            //Text(date).font(.caption).padding(.all)
            HStack {
                if (message.user == "MoreCoffee") {
                    Spacer()
                    Text(message.messageBody).padding(.horizontal)
                }
                else {
                    Text(message.messageBody).padding(.horizontal)
                    Spacer()
                }
            }
            .font(.subheadline)
        }
    }
}

struct MessageRow_Previews: PreviewProvider {
    static var previews: some View {
        MessageRow(message: Message(messageBody: "Message Preview", messageSentDate: Date()))
    }
}
