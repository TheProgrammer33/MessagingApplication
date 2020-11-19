//
//  NewChatView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/9/20.
//

import SwiftUI

struct NewChatView: View {
    
    var body: some View {
        List {
            HStack {
                Text("Add Chat").padding([.leading, .trailing])
                    .padding(.top, 2.0)
                Button(action: {
                    
                }) {
                    Text("+")
                }.padding([.trailing])
                    .padding(.top, 2.0)
            }
        }.frame(width: 170.0, height: 35.0)
    }
}

struct NewChatView_Previews: PreviewProvider {
    static var previews: some View {
        NewChatView()
    }
}
