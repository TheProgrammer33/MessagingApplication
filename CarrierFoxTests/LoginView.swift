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
    var body: some View {
        return VStack {
            Form {
                TextField("Username", text: $username)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()
                TextField("Password", text: $password)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()
            }
            Text("Reset Password")
                .multilineTextAlignment(.center)
            HStack {
                Spacer()
                Button(action: {
                    connectToAPI()
                }) {
                    Text("Submit")
                }.padding([.top, .bottom, .trailing])
            }
            Text("Invalid Username or Password").font(.footnote).padding()
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
