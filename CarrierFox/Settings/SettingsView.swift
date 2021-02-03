//
//  SettingsView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/21/20.
//

import SwiftUI

struct SettingsView: View {
    @ObservedObject var userData: UserData = .shared
    let myWindow:NSWindow?
    var body: some View {
        VStack {
            HStack {
                VStack {
                    AccountSettings()
                    Spacer()
                }
                Divider().padding()
                VStack {
                    NotificationsLogoutView(myWindow: myWindow)
                    Spacer()
                }
            }
            
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView(myWindow: nil)
    }
}
