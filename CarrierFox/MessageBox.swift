//
//  messageBoxDetail.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/21/20.
//

import SwiftUI

struct MessageBox: View {
    @State private var message = ""
    @EnvironmentObject var userData: UserData
    var body: some View {
        HStack {
            TextField("Type here", text: $message)
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            Button(action: {
                if(message != "") {
                    sendMessage(myMessage: message)
                }
                getMessages() { (messages) in
                    userData.publishMessageChanges(messages: updateMessages(messages))
                }
                message = ""
            }) {
                Text("Send")
            }.padding([.top, .bottom, .trailing])
        }
    }
}


struct messageBox_Previews: PreviewProvider {
    static var previews: some View {
        MessageBox().environmentObject(UserData())
    }
}
