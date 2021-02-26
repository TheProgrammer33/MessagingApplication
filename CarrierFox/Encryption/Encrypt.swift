//
//  Encrypt.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 1/19/21.
//

import Foundation

func encryptData() throws{
    
    let pathString = "privatekey.txt"
    let path = URL(fileURLWithPath: pathString)
//    var data: Data
//    if let path = Bundle.main.path(forResource: "carrierfoxprivatekey", ofType: "txt") {
//        if let stringPrivateKey = try? String(contentsOfFile: path) {
//            data = Data(stringPrivateKey.utf8)
//            print(String(data: data, encoding: String.Encoding.utf8) as Any)
//        }
//        else {
//            print("error reading file")
//        }
//    }
//    else {
//        print("error reading path")
//    }
    
    
    //let privateKeyFromFile = try Data(contentsOf: path)
    
//    let privateKeyString = "-----BEGIN RSA PRIVATE KEY-----MIIEowIBAAKCAQEA4KXCtBGH/sAES/m7R9FyGbn5SdQNXIhvNMx6YPqB01aH+THfUaxagpU5nT7HWkskB6Hh4wGS1B+WYm81zFne46E+VMlqrTxshaTSqj4ndr2hvvvHfCKBlgFWdPV7OL8/zH88+yMQ0oi7hQDx92BN+sxtBLnCIA8V95Pk9W7VPaIHIhyzv5AVQ6OZmCuKbao4mbHjaN2/P21ILE9ijhh5I7cBCJ5sJcw92qEL/YSbJNrNa2bhMW3W2e5vpmkfKl/EHPMSIZZDSniwqjBg+zVSU6ZBzuH3v74X7wl1fEFoQbl20W3XHrryyb0v9vZk2lJb5TQ6l6Ojmd3QdOoiLTrs6QIDAQABAoIBABdsgUhIWa9Q80G+yO3co3KWRy2RDFGdMkdFmO8av8W7DJOJxTjdaVSbXbYNMTkzg46+LPEAHYC7yHD8GpZzun9utTKfC94DYmdu/75bm77QWBizZSIG8Q8klZC9dfs9Sdg3XjLrfZoOQstEBXbfVlIz1Zw8CEdSe15kz+ddhqOV2umIB/mfRZ0dHYEGWfokPOJIk84j4RJWxQItwopweM0Izg8CILZq+kR3VuVqQ1pKSdDgTEpe5r6t1ZJ960mOw3TO2uciuVFm9FGFkAkLWkMo/nP2NSlgGsqJ8oD3uSBccmex8t/GXv8bnoHPKsD/dmf8F4iYnDg02YT+77I6IgcCgYEA+86KFYd221NEY2caiiCNpLRLQWVmpuI1uf31aZC5Pvo9uutdsjtwg+3XgOq1rZzjzV5Kgj8jmu6coldbIjcgzcBBF7vsTw42h9zyS/+9cRGQdzpajYVFgyT5/IZv3pcGH/bbyWqAWJeL5uVcw3iJzqCCNBEuv5rJ9pL7J+RB/PsCgYEA5GNwsgy+w6fHnYIMCY4B4n3bI06lQoBTSIpLCRwg0wvWQJh1w83JOOMxYDAneqtJZR5u1p00mFrcdcP06ktzEnmax+qwP94RSAJGbxgmZ/3Wa1Vcdaa/FFMzpBUvUlTfB4C9rQ+ln13elShjuDDBcAMZQLKLaZgz8rBCh9S7kGsCgYEA9yxPuUBm+HXs/JI5p/q6b0GXWZM6x3fnezIB02T/ogUr0S62o3zbPnWag+5g9hG6P15eTynBPpUY5fZP++8XYUBl5PFPzm5KMuU8ZkOcZr/yCkcami6HBGuohCxG5b1jgr7kEftcDxp7duQQQcQxqcU+6fMv6176/vJaXVcfOz8CgYAfLBdopeavq8nKDML51uEY+dacJhLWVc+IOh/tuWIdjCMNJAIG3KKXWiMWHtx0mMZmv9WfA0v3c4ddp4ZZKD0ni2N93+F4hiXTr0PPy/sJ/JJ0ie0Zvc2cx5Jn1eBkm2Lpkj/RTC5t5xEHSMD7leQBFtsugnAW1IhamoYqJfYHywKBgAb7MY2srvwFif8Cx3JGLVWixaNY/fgwNveT7w3StgOwldGbJ58iFZzafUZ/646Y27UNjTg00gDSgrXVCfOjT8FeavT+xjk4dp1AhmgHjMTOerKsfi+hTARzeCVnc7Jt13yZkYAMqeb2MQkR9q00joBw3Bx1LLTa85bHjOqFR3Hi-----END RSA PRIVATE KEY-----"
    
    let privateKeyString = "-----BEGIN PUBLIC KEY-----MIIBCgKCAQEAkZrbWUk5NOy3MCnHHY09hPcuh90zImlbM8bBMPXAZK+nFRyVPkH8+NZq/3uJyXITfPgdqPGsNvGedV1KuEg54KQLeuqKXG7tl63TmkKiZPqAqnhg2lbdt+VBBOBg1oyxJ51YoQhJnqJtPHYl7CEgXBOUT0wXIo/GWy/ulAeIRvZQbyQ6nKAMDkx7CR5DnhF3g29K06fnVN/FlvKS7UELuQ0eMF2/nfeegloNyxp7WR8p/Ydc+nr55Pfr7F5ehkqkGDE8Y/DdkndqLTF94674S16btORB1W5qjWVUsiignm5vVHGmR27VhEB4OFZ93oDv5xoQ4foqKF95VlYo5eu6wQIDAQAB-----END PUBLIC KEY-----"
    let privateKeyData = Data(privateKeyString.utf8)
    print(String(data: privateKeyData, encoding: String.Encoding.utf8) as Any)
    
    let keyDict:[NSObject:NSObject] = [
      kSecAttrKeyType: kSecAttrKeyTypeRSA,
      kSecAttrKeyClass: kSecAttrKeyClassPublic,
      kSecAttrKeySizeInBits: NSNumber(value: 2048),
      kSecReturnPersistentRef: true as NSObject
     ]
    var creationError: Unmanaged<CFError>?
    
    guard let key = SecKeyCreateWithData(privateKeyData as CFData,
                                         keyDict as CFDictionary,
                                         &creationError) else {
                                            throw creationError!.takeRetainedValue() as Error
    }
//    guard let privateKey = SecKeyCreateRandomKey(keyDict as CFDictionary, &creationError) else {
//        throw creationError!.takeRetainedValue() as Error
//    }
//
//    let publicKey = SecKeyCopyPublicKey(privateKey)
//
//    let message = "Hello World"
//    let messageData = Data(message.utf8)
//    var error: Unmanaged<CFError>? = nil
//
//    if(publicKey != nil) {
//        guard let cipherText = SecKeyCreateEncryptedData(publicKey!, SecKeyAlgorithm.rsaEncryptionPKCS1, messageData as CFData, &error) else { return }
//
//        if error != nil {
//            print(error?.takeRetainedValue() as Any)
//        }
//
//        print("cipherText: \(String(describing: cipherText))")
//
//    }
//    else {
//        print("Error creating public key")
//    }
}
