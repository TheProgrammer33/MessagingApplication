//
//  NewChatView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/9/20.
//

import SwiftUI

struct NewChatView: View {
    var body: some View {
        HStack {
            Text("Add Chat").padding()
            Button(action: /*@START_MENU_TOKEN@*//*@PLACEHOLDER=Action@*/{}/*@END_MENU_TOKEN@*/) {
                Text("+")
            }.padding([.top, .bottom, .trailing])
        }.frame(width: 170.0, height: 35.0)
    }
}

struct NewChatView_Previews: PreviewProvider {
    static var previews: some View {
        NewChatView()
    }
}
