//
//  NotificationsLogoutView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 12/10/20.
//

import SwiftUI

struct NotificationsLogoutView: View {
    @ObservedObject var userData: UserData = .shared
    let myWindow:NSWindow?
    var body: some View {
        return VStack {
            NotificationsSettings()
            Divider().padding()
            Logout(myWindow: myWindow).padding(.top)
        }.padding()    }
}

struct NotificationsLogoutView_Previews: PreviewProvider {
    static var previews: some View {
        NotificationsLogoutView(myWindow: nil)
    }
}
