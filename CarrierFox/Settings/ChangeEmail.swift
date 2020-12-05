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
        let handlerBlock: (Bool) -> Void = {
            if $0 {
                validEmail = true
            }
            else {
                validEmail = false
            }
        }
        return VStack {
            Text("Change Email")
                .font(.title)
            TextField("Email", text: $email)
                .padding([.top, .leading, .trailing])
                .textFieldStyle(RoundedBorderTextFieldStyle())
            if(!validEmail) {
                Text("Invalid Email")
                    .foregroundColor(Color.red)
            }
            Button(action: {
                print("changing email")
                changeEmail(sessionID: userData.sessionID, newEmail: email, completionHandler: handlerBlock)
            }) {
                Text("Change Email")
            }.padding()
        }
    }
}

struct ChangeEmail_Previews: PreviewProvider {
    static var previews: some View {
        ChangeEmail()
    }
}
