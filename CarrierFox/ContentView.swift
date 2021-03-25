//
//  ContentView.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 9/21/20.
//

import SwiftUI

struct ContentView: View {
    let myWindow:NSWindow?
    @State private var isSettings: Bool = false
    @State private var isFriends: Bool = false
    @ObservedObject var userData: UserData = .shared
    var body: some View {
        return VStack {
            if(isSettings || isFriends)
            {
                HStack {
                    Spacer()
                    Button(action: {
                        self.isFriends = false
                        self.isSettings = false
                    }) {
                        Text(NSLocalizedString("Back", comment: "Back to main page"))
                    }
                }.padding([.top, .leading, .trailing])
            }
            if(!isSettings && !isFriends) {
                HStack {
                    ChatSelectionView()
                    VStack {
                        HStack {
                            List {
                                Text(userData.selectedChatName).multilineTextAlignment(.leading).padding(.top, 2.0)
                            }.frame(height: 35.0)
                            .padding(.leading)
                            Spacer()
                            if(isSettings || isFriends)
                            {
                                Button(action: {
                                    self.isFriends = false
                                    self.isSettings = false
                                }) {
                                    Text(NSLocalizedString("Back", comment: "Back to main page"))
                                }
                            }
                            Spacer()
                            VStack {
                                HStack {
                                    Button(action: {
                                        self.isFriends.toggle()
                                        self.isSettings = false
                                        encryptCommonCrypto()
                                    }) {
                                        Text(NSLocalizedString("Friends", comment: "Friends"))
                                    }
                                    Button(action: {
                                        self.isSettings.toggle()
                                        self.isFriends = false
                                    }) {
                                        Text(NSLocalizedString("Settings", comment: "Settings"))
                                    }
                                }
                                HStack {
                                    SearchMessages()
                                    ChatSettings()
                                }
                            }
                            
                        }.padding([.top, .trailing])
                        MessagingView().environmentObject(UserData())
                    }
                }
            }
            else if(isFriends) {
                FriendsView()
            }
            else {
                SettingsView(myWindow: myWindow)
            }
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(myWindow: nil)
    }
}

