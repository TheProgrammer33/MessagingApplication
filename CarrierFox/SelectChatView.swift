//
//  SelectChatView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/9/20.
//

import SwiftUI

struct SelectChatView: View {
    var body: some View {
        List {
            ChatDetailView()
            ChatDetailView()
            ChatDetailView()
        }
        .frame(width: 170.0)
    }
}

struct SelectChatView_Previews: PreviewProvider {
    static var previews: some View {
        SelectChatView()
    }
}
