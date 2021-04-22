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
    @State private var showMessageSettings: Bool = false
    var body: some View {
        func rightMouseDown(with theEvent: NSEvent) {
        }
        let formatter = DateFormatter()
        formatter.dateFormat = "MM/dd/yyyy h:mm a"
        let date = formatter.string(from: message.messageSentDate)
        return VStack {
            HStack {
                if (message.user == userData.username) {
                    
                    HStack {
                        Spacer()
                        VStack {
                            HStack {
                                Spacer()
                                Text(date).font(.caption).foregroundColor(Color.gray)
                                Text(message.user)
                                    .font(.footnote)
                                    .multilineTextAlignment(.trailing)
                                    .padding(.trailing, 6.0)
                            }
                            ZStack{
                                HStack {
                                    Spacer()
                                    Text(message.messageBody)
                                        .foregroundColor(Color.white)
                                        .multilineTextAlignment(.leading)
                                        .padding(.horizontal)
                                        .background(Color.blue)
                                        .clipShape(Capsule())
                                        .fixedSize(horizontal: false, vertical: true)
                                }
                                    
                                Button(action: {
                                    self.showMessageSettings.toggle()
                                }) {
                                    Text("                                                                          ")
                                        .foregroundColor(Color.white)
                                        
                                }.buttonStyle(BorderlessButtonStyle())
                                .padding(.horizontal)
                                
                            }
                                     
                        } .popover(
                            isPresented: self.$showMessageSettings,
                            arrowEdge: .trailing
                        ) {
                            VStack {
                                Text(NSLocalizedString("Delete Message?", comment: "Confirm delete message"))
                                HStack {
                                    Button(action: {
                                        print(message._id)
                                        deleteMessage(messageID: message._id, threadID: userData.selectedChatID)
                                        self.showMessageSettings.toggle()
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
                                        
                                    }){
                                        Text(NSLocalizedString("Delete", comment: "Delete message"))
                                    }
                                    Button(action: {
                                        self.showMessageSettings.toggle()
                                        
                                    }){
                                        Text(NSLocalizedString("Cancel", comment: "Cancel"))
                                    }
                                }
                            }.padding()
                    }
                    }
                }
                else {
                    VStack {
                        HStack {
                            Text(message.user)
                                .font(.footnote)
                                .multilineTextAlignment(.leading)
                                .padding(.leading, 6.0)
                            Text(date).font(.caption).foregroundColor(Color.gray)
                                .font(.caption)
                                .foregroundColor(Color.white)
                            Spacer()
                        }
                        HStack {
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
