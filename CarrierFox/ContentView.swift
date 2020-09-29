//
//  ContentView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/21/20.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            MessageList(messageList: [MessageRow(message: Message(messageBody: "Message Demo 1", messageSendDate: Date())), MessageRow(message: Message(messageBody: "Message Demo 2", messageSendDate: Date()))])
                .padding([.top, .leading, .trailing])
            MessageBox()
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

