//
//  AccountSettings.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 11/22/20.
//

import SwiftUI

struct AccountSettings: View {

    var body: some View {
        return VStack {
            ChangePassword()
            Divider().padding()
            ChangeEmail().padding(.top)
        }.padding()
    }
}

struct AccountSettings_Previews: PreviewProvider {
    static var previews: some View {
        AccountSettings()
    }
}
