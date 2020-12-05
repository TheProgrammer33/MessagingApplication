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
        }.padding()
    }
}

struct NotificationsSettings_Previews: PreviewProvider {
    static var previews: some View {
        NotificationsSettings()
    }
}
