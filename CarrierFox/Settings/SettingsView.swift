//
//  SettingsView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/21/20.
//

import SwiftUI

struct SettingsView: View {
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        VStack {
            HStack {
                VStack {
                    AccountSettings()
                    Spacer()
                }
                VStack {
                    NotificationsSettings()
                    Spacer()
                }
            }
            Button(action: {
                print("saving settings")
                saveNotifications(sessionID: userData.sessionID, notifications: userData.notificationsIsOn)
            }) {
                Text("Save Settings")
            }
            .padding(.bottom)
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
