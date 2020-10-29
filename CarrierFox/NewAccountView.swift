//
//  NewAccountView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 10/18/20.
//

import SwiftUI

func showLoginWindow() {
    var windowRef:NSWindow
    windowRef = NSWindow(
        contentRect: NSRect(x: 0, y: 0, width: 450, height: 500),
        styleMask: [.titled, .closable, .miniaturizable, .fullSizeContentView],
        backing: .buffered, defer: false)
    windowRef.contentView = NSHostingView(rootView: LoginView(myWindow: windowRef))
    windowRef.makeKeyAndOrderFront(nil)
}

struct NewAccountView: View {
    @State private var username = ""
    @State private var email = ""
    @State private var password = ""
    @State private var confirmPassword = ""
    let myWindow:NSWindow?
    var body: some View {
        var successfulNewAccount: Bool = false
        let handlerBlock: (Bool) -> Void = {
            if $0 {
                successfulNewAccount = true
            }
        }
        return VStack {
            Image("Logo")
                .resizable()
                .frame(width: 128.0, height: 128.0)
                .scaledToFit()
            Form {
                TextField("Username", text: $username)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                if(successfulNewAccount) {
                    Text("Username already exists")
                        .foregroundColor(Color.red)
                        .padding(.leading, 17.0)
                }
                TextField("Email", text: $email)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                if(successfulNewAccount) {
                    Text("Invalid Email")
                        .foregroundColor(Color.red)
                        .padding(.leading, 17.0)
                }
                TextField("Password", text: $password)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                TextField("Confirm Password", text: $confirmPassword)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                if(password != confirmPassword) {
                    Text("Passwords do not match")
                        .foregroundColor(Color.red)
                        .padding(.leading, 17.0)
                }
            }
            .frame(width: 425.0)
            Button(action: {
                createNewAccount(completionHandler: handlerBlock, email: email, username: username, password: password)
                if(successfulNewAccount) {
                    showLoginWindow()
                    self.myWindow?.close()
                }
                else {
                    print("Error creating Account")
                }
            }) {
                Text("Create Account")
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
