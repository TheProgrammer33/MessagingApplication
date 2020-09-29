//
//  messageBoxDetail.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/21/20.
//

import SwiftUI

struct MessageBox: View {
    @State private var message = ""
    var body: some View {
        HStack {
            TextField("Type here", text: $message)
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            Button(action: {
                connectToAPI()
            }) {
                Text("Send")
            }.padding([.top, .bottom, .trailing])
        }
    }
}


struct messageBox_Previews: PreviewProvider {
    static var previews: some View {
        MessageBox()
    }
}
