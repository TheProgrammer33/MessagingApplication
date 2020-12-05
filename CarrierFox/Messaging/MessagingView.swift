//
//  MessagingView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/19/20.
//

import SwiftUI

struct MessagingView: View {
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        VStack {
            MessageList()
                .padding([.leading, .trailing])
            MessageBox()
        }
    }
}

struct MessagingView_Previews: PreviewProvider {
    static var previews: some View {
        MessagingView()
    }
}

