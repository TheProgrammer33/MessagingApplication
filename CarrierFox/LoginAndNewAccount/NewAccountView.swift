//
//  NewAccountView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 10/18/20.
//

import SwiftUI

struct NewAccountView: View {
    @State private var username = ""
    @State private var email = ""
    @State private var password = ""
    @State private var confirmPassword = ""
    @State private var successfulNewAccount: Bool = false;
    let myWindow:NSWindow?
    var body: some View {
        return VStack {
            Image("Logo")
                .resizable()
                .frame(width: 128.0, height: 128.0)
                .scaledToFit()
            Form {
                TextField(NSLocalizedString("Username", comment: "Username"), text: $username)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                if(successfulNewAccount) {
                    Text(NSLocalizedString("Username already exists", comment: "Username already exists"))
                        .foregroundColor(Color.red)
                        .padding(.leading, 17.0)
                }
                TextField(NSLocalizedString("Email", comment: "Email"), text: $email)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                if(successfulNewAccount) {
                    Text(NSLocalizedString("Invalid Email", comment: "Invalid Email"))
                        .foregroundColor(Color.red)
                        .padding(.leading, 17.0)
                }
                SecureField(NSLocalizedString("Password", comment: "Password"), text: $password)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                SecureField(NSLocalizedString("Confirm Password", comment: "Confirm Password"), text: $confirmPassword)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                if(password != confirmPassword) {
                    Text(NSLocalizedString("Passwords do not match", comment: "Passwords do not match"))
                        .foregroundColor(Color.red)
                        .padding(.leading, 17.0)
                }
            }
            .frame(width: 425.0)
            Button(action: {
                createNewAccount(email: email, username: username, password: password){ response in
                    successfulNewAccount = response
                }
                if(successfulNewAccount) {
                    showLoginWindow()
                    self.myWindow?.close()
                }
                else {
                    print("Error creating Account")
                }
            }) {
                Text(NSLocalizedString("Create Account", comment: "Create Account"))
            }.padding()
        }
        .frame(width: 450.0, height: 500.0)
    }
}

struct NewAccountView_Previews: PreviewProvider {
    static var previews: some View {
        NewAccountView(myWindow: nil)
    }
}
