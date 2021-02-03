//
//  ChangeWindow.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 1/3/21.
//

import Foundation
import SwiftUI

func showLoginWindow() {
    var windowRef:NSWindow
    windowRef = NSWindow(
        contentRect: NSRect(x: 0, y: 0, width: 450, height: 500),
        styleMask: [.titled, .closable, .miniaturizable, .fullSizeContentView],
        backing: .buffered, defer: false)
    windowRef.contentView = NSHostingView(rootView: LoginView(myWindow: windowRef))
    windowRef.makeKeyAndOrderFront(nil)
    windowRef.center()
}


func showContentWindow() {
    print("in show content window")
    var windowRef:NSWindow
    windowRef = NSWindow(
        contentRect: NSRect(x: 0, y: 0, width: 800, height: 700),
        styleMask: [.titled, .closable, .miniaturizable, .fullSizeContentView, .resizable],
        backing: .buffered, defer: false)
    windowRef.contentView = NSHostingView(rootView: ContentView(myWindow: windowRef))
    windowRef.makeKeyAndOrderFront(nil)
    windowRef.center()
}

func showNewAccountWindow() {
    var windowRef:NSWindow
    windowRef = NSWindow(
        contentRect: NSRect(x: 0, y: 0, width: 450, height: 500),
        styleMask: [.titled, .closable, .miniaturizable, .fullSizeContentView],
        backing: .buffered, defer: false)
    windowRef.contentView = NSHostingView(rootView: NewAccountView(myWindow: windowRef))
    windowRef.makeKeyAndOrderFront(nil)
    windowRef.center()
}

