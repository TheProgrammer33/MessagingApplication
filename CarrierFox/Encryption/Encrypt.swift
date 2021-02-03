//
//  Encrypt.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 1/19/21.
//

import Foundation

func encryptData() throws{
//    let serverPublicKey = "help"
//    let utf8Data = Data(serverPublicKey.utf8)
//    let base64Data = utf8Data.base64EncodedData()

    
    let keyDict:[NSObject:NSObject] = [
      kSecAttrKeyType: kSecAttrKeyTypeRSA,
      kSecAttrKeyClass: kSecAttrKeyClassPublic,
      kSecAttrKeySizeInBits: NSNumber(value: 2048),
      kSecReturnPersistentRef: true as NSObject
     ]
    
    var creationError: Unmanaged<CFError>?
    guard let privateKey = SecKeyCreateRandomKey(keyDict as CFDictionary, &creationError) else {
        throw creationError!.takeRetainedValue() as Error
    }
    
    let publicKey = SecKeyCopyPublicKey(privateKey)
    
//    print("Base64 data: \(utf8Data.base64EncodedString())")
//    let publickeysi = SecKeyCreateWithData(base64Data as CFData, keyDict as CFDictionary, nil)
    
    let message = "Hello World"
    let messageData = Data(message.utf8)
    var error: Unmanaged<CFError>? = nil
    
    if(publicKey != nil) {
        guard let cipherText = SecKeyCreateEncryptedData(publicKey!, SecKeyAlgorithm.rsaEncryptionPKCS1, messageData as CFData, &error) else { return }
        
        if error != nil {
            print(error?.takeRetainedValue() as Any)
        }
        
        print("cipherText: \(String(describing: cipherText))")
        
//        let plainText = SecKeyCreateDecryptedData(publicKey!, SecKeyAlgorithm.rsaEncryptionPKCS1, cipherText, &error)
//
//        if error != nil {
//            print(error?.takeRetainedValue() as Any)
//        }
//
//        print("plainText: \(String(describing: plainText))")
    }
    else {
        print("Error creating public key")
    }
}
