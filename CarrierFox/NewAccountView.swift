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
    var body: some View {
        VStack {
            Image("AppIcon")
                .resizable()
                .scaledToFit()
            TextField("Username", text: $username)
                .padding()
                .frame(width: 425.0)
            TextField("Email", text: $email)
                .padding()
                .frame(width: 425.0)
            TextField("Password", text: $password)
                .padding()
                .frame(width: 425.0)
            TextField("Confirm Password", text: $confirmPassword)
                .padding()
                .frame(width: 425.0)
            Button(action: /*@START_MENU_TOKEN@*//*@PLACEHOLDER=Action@*/{}/*@END_MENU_TOKEN@*/) {
                Text("Create Account")
            }.padding()
        }
        .frame(width: 450.0, height: 500.0)
    }
}

struct NewAccountView_Previews: PreviewProvider {
    static var previews: some View {
        NewAccountView()
    }
}
