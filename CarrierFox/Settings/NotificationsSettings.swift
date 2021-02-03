//
//  NotificationsSettings.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/22/20.
//

import SwiftUI

struct NotificationsSettings: View {
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        VStack {
            Text("Notification Settings")
                .font(.title)
            Toggle(isOn: $userData.notificationsIsOn) {
                Text("Send Notifications")
            }.toggleStyle(SwitchToggleStyle())
            Button(action: {
                print("saving settings")
                saveNotifications(sessionID: userData.sessionID, notifications: userData.notificationsIsOn)
            }) {
                Text("Save Settings")
            }
            .padding(.vertical)
        }.padding()
    }
}

struct NotificationsSettings_Previews: PreviewProvider {
    static var previews: some View {
        NotificationsSettings()
    }
}
