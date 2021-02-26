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
//            let point = NSPoint { NSEvent.mouseLocation }
//            let row = List.row(at: point)
//            print("right click")
//            print(row)
        }
        let formatter = DateFormatter()
        formatter.dateFormat = "MM/dd/yyyy h:m a"
        let date = formatter.string(from: message.messageSentDate)
        return VStack {
            HStack {
                if (message.user == userData.username) {
                    
                    VStack {
                        HStack {
                            Spacer()
                            Text(date).font(.caption).foregroundColor(Color.gray)
                            Text(message.user)
                                .font(.footnote)
                                .multilineTextAlignment(.trailing)
                                .padding(.trailing, 6.0)
                        }
                        HStack {
                            Spacer()
                            Button(action: {
                                self.showMessageSettings.toggle()
                            }) {
                                Text(message.messageBody)
                                    .foregroundColor(Color.white)
                            }.buttonStyle(BorderlessButtonStyle())
                            //.fixedSize(horizontal: false, vertical: true)
                            .multilineTextAlignment(.trailing)
                            .padding(.horizontal)
                             .background(Color.blue)
                             .clipShape(Capsule())
                             
                        }
                    } .popover(
                        isPresented: self.$showMessageSettings,
                        arrowEdge: .trailing
                    ) {
                        VStack {
                            Text("Delete Message?")
                            HStack {
                                Button(action: {
                                    print("deleting message")
                                    print(message._id)
                                    deleteMessage(messageID: message._id, threadID: userData.selectedChatID)
                                    self.showMessageSettings.toggle()
                                    
                                }){
                                    Text("Delete")
                                }
                                Button(action: {
                                    self.showMessageSettings.toggle()
                                    
                                }){
                                    Text("Cancel")
                                }
                            }
                        }.padding()
                    }
                }
                else {
                    VStack {
                        HStack {
                            Text(message.user)
                                .font(.footnote)
                                .multilineTextAlignment(.leading)
                                .padding(.leading, 6.0)
                            Text(date)
                                .font(.caption)
                                .foregroundColor(Color.gray)
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
