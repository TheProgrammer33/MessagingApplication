//
//  ChangePassword.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/22/20.
//

import SwiftUI

struct ChangePassword: View {
    @State private var oldPassword = ""
    @State private var newPassword = ""
    @State private var confirmNewPassword = ""
    @State private var validOldPassword = false
    @State private var showAlert: Bool = false
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        return VStack {
            Text(NSLocalizedString("Change Password", comment: "Change Password")).font(.title)
            
            SecureField(NSLocalizedString("Old Password", comment: "Old Password"), text: $oldPassword)
                .padding([.top, .leading, .trailing])
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            SecureField(NSLocalizedString("New Password", comment: "New Password"), text: $newPassword)
                .padding([.top, .leading, .trailing])
                .textFieldStyle(RoundedBorderTextFieldStyle())
            SecureField(NSLocalizedString("Confirm New Password", comment: "Confirm New Password"), text: $confirmNewPassword)
                .padding([.leading, .bottom, .trailing])
                .textFieldStyle(RoundedBorderTextFieldStyle())
            if(newPassword != confirmNewPassword) {
                Text(NSLocalizedString("Passwords do not match", comment: "Passwords do not match"))
                    .foregroundColor(Color.red)
            }
            
            Button(action: {
                changePassword(sessionID: userData.sessionID, password: newPassword, oldPassword: oldPassword) { response in
                    self.showAlert = !response
                }
            }) {
                Text(NSLocalizedString("Change Password", comment: "Change Password"))
            }
        }.alert(isPresented: $showAlert) {
            Alert(title: Text(NSLocalizedString("Password is incorrect", comment: "Invalid")), message: Text(NSLocalizedString("Password is incorrect", comment: "Invalid")), dismissButton: .default(Text("Okay")))
        }
        
    }
}

struct ChangePassword_Previews: PreviewProvider {
    static var previews: some View {
        ChangePassword()
    }
}
