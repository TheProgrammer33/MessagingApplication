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
    @EnvironmentObject var userData: UserData
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
                        Text("Back")
                    }
                }.padding([.top, .leading, .trailing])
            }
            if(!isSettings && !isFriends) {
                HStack {
                    ChatSelectionView()
                    VStack {
                        HStack {
                            List {
                                Text("Septri").multilineTextAlignment(.leading).padding(.top, 2.0)
                            }.frame(height: 35.0)
                            .padding(.leading)
                            Spacer()
                            if(isSettings || isFriends)
                            {
                                Button(action: {
                                    self.isFriends = false
                                    self.isSettings = false
                                }) {
                                    Text("Back")
                                }
                            }
                            Spacer()
                            Button(action: {
                                self.isFriends.toggle()
                                self.isSettings = false
                            }) {
                                Text("Friends")
                            }
                            Button(action: {
                                self.isSettings.toggle()
                                self.isFriends = false
                            }) {
                                Text("Settings")
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
                SettingsView()
            }
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(myWindow: nil)
    }
}

