//
//  MessageRow.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/28/20.
//

import SwiftUI

struct MessageRow: View {
    var message: Message
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        let formatter = DateFormatter()
        formatter.dateFormat = "MM/dd/yyyy h:m a"
        let date = formatter.string(from: message.messageSentDate)
        return VStack {
            //Text(date).font(.caption).padding(.all)
            HStack {
                if (message.user == userData.username) {
                    Spacer()
                    Text(message.messageBody)
                        .foregroundColor(Color.white)
                        .multilineTextAlignment(.trailing).padding(.horizontal)
                        .background(Color.blue)
                        .clipShape(Capsule())
                        .fixedSize(horizontal: false, vertical: true)
                }
                else {
                    Text(message.messageBody)
                        .foregroundColor(Color.white)
                        .multilineTextAlignment(.leading)
                        .padding(.horizontal)
                        .background(Color.gray)
                        .clipShape(Capsule())
                        .fixedSize(horizontal: false, vertical: true)
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
