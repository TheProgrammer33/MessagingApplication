//
//  NewChatSelectionView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/10/20.
//

import SwiftUI

struct NewChatSelectionView: View {
    var body: some View {
        VStack {
            Picker(selection: /*@START_MENU_TOKEN@*/.constant(1)/*@END_MENU_TOKEN@*/, label: Text("Select User")) /*@START_MENU_TOKEN@*/{
                Text("James").tag(1)
                Text("Joe").tag(2)
                Text("Jack").tag(3)
                Text("Dylan").tag(4)
                Text("Kaven").tag(5)
                Text("Chris").tag(6)
                Text("Katie").tag(7)
                Text("Anna").tag(8)
                Text("Katie again").tag(9)
                Text("Thomas").tag(10)
            }.padding()
        }/*@END_MENU_TOKEN@*/
    }
}

struct NewChatSelectionView_Previews: PreviewProvider {
    static var previews: some View {
        NewChatSelectionView()
    }
}
