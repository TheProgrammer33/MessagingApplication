//
//  ChatDetailView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/9/20.
//

import SwiftUI

struct ChatDetailView: View {
    var body: some View {
        VStack {
            Text("Friend 1")
                .padding()
            Divider()
        }.frame(width: 150.0, height: 60.0)
    }
}

struct ChatDetailView_Previews: PreviewProvider {
    static var previews: some View {
        ChatDetailView()
    }
}
