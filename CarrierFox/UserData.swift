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
    //@Published var messages = messageData
    @Published var messages = [Message(message: "Test message 1"), Message(message: "Test message 2")]//[Message(messageBody: "Message Demo 1", messageSendDate: Date()), Message(messageBody: "Message Demo 2", messageSendDate: Date())]
}