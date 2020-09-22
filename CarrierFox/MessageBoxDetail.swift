//
//  messageBoxDetail.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/21/20.
//

import SwiftUI

struct MessageBoxDetail: View {
    @State private var message = ""
    var body: some View {
        HStack {
            TextField("Type here", text: $message)
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            Button(action: /*@START_MENU_TOKEN@*//*@PLACEHOLDER=Action@*/{}/*@END_MENU_TOKEN@*/) {
                Text("Send")
            }.padding([.top, .bottom, .trailing])
        }
    }
}


struct messageBoxDetail_Previews: PreviewProvider {
    static var previews: some View {
        MessageBoxDetail()
    }
}
