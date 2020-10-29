//
//  UserData.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 10/17/20.
//

import Foundation

import SwiftUI
import Combine

final class UserData: ObservableObject {
    @Published var messages = messageData
    @Published var username = "MoreCoffee"
}
