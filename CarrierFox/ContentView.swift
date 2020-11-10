//
//  ContentView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/21/20.
//

import SwiftUI

struct ContentView: View {
    let myWindow:NSWindow?
    var body: some View {
        return VStack {
            HStack {
                NewChatView()
                Spacer()
            }
            HStack {
                SelectChatView().padding([.top, .leading, .bottom])
                VStack {
                    MessageList()
                        .padding([.top, .leading, .trailing])
                    MessageBox()
                }
            }
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(myWindow: nil)
    }
}

