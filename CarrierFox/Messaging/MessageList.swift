//
//  MessageHistoryDetail.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/28/20.
//

import SwiftUI

struct MessageList: View {
    @ObservedObject var userData: UserData = .shared
    @State var scrollTo : CGPoint? = nil //CGPoint?(x: 0, y: 10000)
    //@State var scrollTo: CGPoint?
    var body: some View {
        //self.scrollTo? = CGPoint(x: 0,y: 30*userData.messages.count)
        return VStack {
            ScrollableView(scrollTo: $scrollTo)//userData.scrollIndex!)
            {
                ForEach(userData.messages, id: \.self) { message in
                    MessageRow(message: message).padding(.all, 5.0).frame(minWidth: 550, maxWidth: 550)
                }
            }
            Button(action: {
                self.$scrollTo.wrappedValue = CGPoint(x: 0,y: 30*userData.messages.count)
            }){
                Text("Scroll to bottom")
            }
        }
    }
}

struct MessageList_Previews: PreviewProvider {
    static var previews: some View {
        MessageList().environmentObject(UserData())
    }
}

