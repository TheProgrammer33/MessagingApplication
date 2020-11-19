//
//  ChatSelectionView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/19/20.
//

import SwiftUI

struct ChatSelectionView: View {
    var body: some View {
        VStack {
            NewChatView()
            NewChatSelectionView()
            SelectChatView()
        }.padding()
    }
}

struct ChatSelectionView_Previews: PreviewProvider {
    static var previews: some View {
        ChatSelectionView()
    }
}
