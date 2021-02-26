//
//  ChangeEmail.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/22/20.
//

import SwiftUI

struct ChangeEmail: View {
    @State private var email = ""
    @State private var validEmail = false
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        return VStack {
            Text(NSLocalizedString("Change Email", comment: "Change Email"))
                .font(.title)
            TextField(NSLocalizedString("Email", comment: "Email"), text: $email)
                .padding([.top, .leading, .trailing])
                .textFieldStyle(RoundedBorderTextFieldStyle())
            if(!validEmail) {
                Text(NSLocalizedString("Invalid Email", comment: "Invalid Email"))
                    .foregroundColor(Color.red)
            }
            Button(action: {
                print("changing email")
                changeEmail(sessionID: userData.sessionID, newEmail: email) { response in
                    validEmail = response
                }
            }) {
                Text(NSLocalizedString("Change Email", comment: "Change Email"))
            }.padding()
        }
    }
}

struct ChangeEmail_Previews: PreviewProvider {
    static var previews: some View {
        ChangeEmail()
    }
}
