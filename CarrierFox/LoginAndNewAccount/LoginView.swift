//
//  LoginMockUp.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 10/12/20.
//

import SwiftUI

struct LoginView: View {
    @State private var username = ""
    @State private var password = ""
    @State private var sessionID = ""
    @ObservedObject var userData: UserData = .shared
    let myWindow:NSWindow?
    var body: some View {
        var successfulLogin: Bool = true
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
                        login(username: username, password: password) { (loginResult) in
                            if loginResult._id != "" {
                                print(loginResult)
                                userData.publishSessionIDChange(id: loginResult._id)
                                userData.publishUsernameChanges(username: self.username)
                                if (!loginResult.threads.isEmpty)
                                {
                                    getMessages(threadID: loginResult.threads[0].threadId) { (messages) in
                                        if(!messages.isEmpty)
                                        {
                                            userData.publishMessageChanges(messages: updateMessages(messages))
                                        }
                                        else
                                        {
                                            userData.publishMessageChanges(messages: [])
                                        }
                                    }
                                }
                                
                                userData.publishFriendListChanges(friendList: loginResult.friends)
                                userData.publishChatChanges(chats: loginResult.threads)
                                userData.publishNotificationChanges(notifications: loginResult.notifications)
                                DispatchQueue.main.async {
                                    self.myWindow?.close()
                                    showContentWindow()
                                }
                                successfulLogin = true
                            }
                            else {
                                successfulLogin = false
                            }
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
