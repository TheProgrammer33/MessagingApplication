//
//  LoginMockUp.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 10/12/20.
//

import SwiftUI

func showContentWindow() {

    var windowRef:NSWindow
    windowRef = NSWindow(
        contentRect: NSRect(x: 0, y: 0, width: 600, height: 500),
        styleMask: [.titled, .closable, .miniaturizable, .fullSizeContentView, .resizable],
        backing: .buffered, defer: false)
    windowRef.contentView = NSHostingView(rootView: ContentView(myWindow: windowRef).environmentObject(UserData()))
    windowRef.makeKeyAndOrderFront(nil)
}

func showNewAccountWindow() {
    var windowRef:NSWindow
    windowRef = NSWindow(
        contentRect: NSRect(x: 0, y: 0, width: 450, height: 500),
        styleMask: [.titled, .closable, .miniaturizable, .fullSizeContentView],
        backing: .buffered, defer: false)
    windowRef.contentView = NSHostingView(rootView: NewAccountView(myWindow: windowRef))
    windowRef.makeKeyAndOrderFront(nil)
}


struct LoginView: View {
    @State private var username = ""
    @State private var password = ""
    @EnvironmentObject var userData: UserData
    let myWindow:NSWindow?
    var body: some View {
        var successfulLogin: Bool = false
        let handlerBlock: (Bool) -> Void = {
            if $0 {
                successfulLogin = true
            }
            else {
                successfulLogin = false
            }
        }
        return NavigationView {
            VStack {
                Image("Logo")
                    .resizable()
                    .frame(width: 128.0, height: 128.0)
                    .scaledToFit()
                Form {
                    TextField("Username", text: $username)
                                .textFieldStyle(RoundedBorderTextFieldStyle())
                        .padding()
                    SecureField("Password", text: $password)
                                .textFieldStyle(RoundedBorderTextFieldStyle())
                        .padding()
                }
                .frame(width: 425.0)
                HStack {
                    Text("Reset Password")
                        .padding(.horizontal)
                    Button(action: {
                        showNewAccountWindow()
                        self.myWindow?.close()
                    }) {
                        Text("Add new Account")
                    }
                }.multilineTextAlignment(.center)
                HStack {
                    Spacer()
                    Button(action: {
                        login(username: username, password: password, completionHandler: handlerBlock)
                        if(successfulLogin) {
                            showContentWindow()
//                            getMessages() { (messages) in
//                                userData.messages = updateMessages(messages)
//                            }
                            self.myWindow?.close()
                        }
                        else {
                            print("Unsucessful Login")
                        }
                    }) {
                        Text("Submit")
                    }
                }
                .padding(.trailing, 27.0)
                if (!successfulLogin) {
                    Text("Invalid Username or Password").font(.footnote).foregroundColor(Color.red).padding()
                }
            }
            .frame(width: 450.0, height: 500.0)
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(myWindow: nil).environmentObject(UserData())
    }
}
