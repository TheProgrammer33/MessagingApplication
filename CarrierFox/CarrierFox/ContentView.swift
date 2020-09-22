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
            Text("Carrier Fox")
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .padding()
            MessageBoxDetail()
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

