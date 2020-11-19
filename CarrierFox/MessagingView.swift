//
//  MessagingView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/19/20.
//

import SwiftUI

struct MessagingView: View {
    var body: some View {
        VStack {
            HStack {
                List {
                    Text("Septri").multilineTextAlignment(.leading).padding(.top, 2.0)
                }.frame(height: 35.0)
                .padding(.leading)
                Spacer()
                Button(action: /*@START_MENU_TOKEN@*//*@PLACEHOLDER=Action@*/{}/*@END_MENU_TOKEN@*/) {
                    Text("X")
                }.padding([.top, .bottom, .trailing])
            }
            .padding(.top, 7)
            MessageList()
                .padding([.leading, .trailing])
            MessageBox()
        }
    }
}

struct MessagingView_Previews: PreviewProvider {
    static var previews: some View {
        MessagingView()
    }
}
