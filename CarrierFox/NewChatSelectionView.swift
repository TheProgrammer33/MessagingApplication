//
//  NewChatSelectionView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/10/20.
//

import SwiftUI

struct NewChatSelectionView: View {
    var body: some View {
        MenuButton(label: Text("Select a Contact")) {
                Text("Joe")
                Text("Jim")
                Text("Jon")
        }.padding([.bottom]).frame(width: 170).grayscale(0.90)
    }
}

struct NewChatSelectionView_Previews: PreviewProvider {
    static var previews: some View {
        NewChatSelectionView()
    }
}
