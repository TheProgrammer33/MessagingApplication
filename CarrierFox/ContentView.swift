//
//  ContentView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/21/20.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            MessageList()
                .padding([.top, .leading, .trailing])
            MessageBox()
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

