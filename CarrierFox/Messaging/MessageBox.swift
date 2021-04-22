//
//  messageBoxDetail.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/21/20.
//

import SwiftUI

struct MessageBox: View, WebSocketConnectionDelegate {
    
    func onConnected(connection: WebSocketConnection) {
        print("Connection")
    }
    
    func onDisconnected(connection: WebSocketConnection, error: Error?) {
        print("Disconnected")
        print(error as Any)
    }
    
    func onError(connection: WebSocketConnection, error: Error) {
        print(error as Any)
    }
    
    func onMessage(connection: WebSocketConnection, text: String) {
        print(text)
        if(text != "Websocket Connected" && text != "Connection") {
            getMessages(threadID: userData.selectedChatID) { (messages) in
                if(!messages.isEmpty)
                {
                    userData.publishMessageChanges(messages: updateMessages(messages))
                    //self.setNotification()
                }
                
            }
            
        }
    }
    
    func onMessage(connection: WebSocketConnection, data: Data) {
        let stringData = String(decoding: data, as: UTF8.self)
        print(stringData)
        userData.messages.append(updateMessages(data))

    }
    
//    func setNotification() -> Void {
//        let manager = NotificationManager()
//        manager.requestPermission()
//        manager.addNotification(title: "This is a test reminder")
//        manager.scheduleNotifications()
//    }
    
    @State private var message = ""
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        let webSocketConnection = WebSocketTaskConnection()
        webSocketConnection.delegate = self
        webSocketConnection.connect()
        return HStack {
            TextField(NSLocalizedString("Type here", comment: "Type message here"), text: $message)
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            Button(action: {
                
                if(message != "") {
                    webSocketConnection.sendMessage(textMessage: message, user: userData.username, threadId: userData.selectedChatID)
                }
                getMessages(threadID: userData.selectedChatID) { (messages) in
                    if(!messages.isEmpty)
                    {
                        userData.publishMessageChanges(messages: updateMessages(messages))
                        userData.updateScrollIndex()
                    }
                    else
                    {
                        userData.publishMessageChanges(messages: [])
                    }
                }
                message = ""
            }) {
                Text(NSLocalizedString("Send", comment: "Sent message"))
            }.padding([.top, .bottom, .trailing])
        }
    }
}


struct messageBox_Previews: PreviewProvider {
    static var previews: some View {
        MessageBox()
    }
}
