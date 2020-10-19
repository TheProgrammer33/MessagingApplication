//
//  Encryption.swift
//  CarrierFox
//
//  Created by Catherine Gallaher on 10/18/20.
//

import Foundation

func generateKey() throws {
    //let tag = "com.test.keys.mykey".data(using: .utf8)!
    let attributes:[NSObject:NSObject] = [
      kSecAttrKeyType: kSecAttrKeyTypeRSA,
      kSecAttrKeyClass: kSecAttrKeyClassPublic,
      kSecAttrKeySizeInBits: NSNumber(value: 2048),
      kSecReturnPersistentRef: true as NSObject
     ]
    var error: Unmanaged<CFError>?
    guard let privateKey = SecKeyCreateRandomKey(attributes as CFDictionary, &error) else {
        throw error!.takeRetainedValue() as Error
    }
    
    let publicKey = SecKeyCopyPublicKey(privateKey)
    
    
    print(privateKey)
    print("\n\n\n")
    print(publicKey as Any)
    
    var encryptionError: Unmanaged<CFError>?
    let algorithm:SecKeyAlgorithm = .rsaEncryptionPKCS1
    let data = "Hello World"
    let plaintext = data.data(using: .utf8)! as CFData
    
    guard let cipherText = SecKeyCreateEncryptedData(publicKey!, algorithm, plaintext, &encryptionError) else {
        throw encryptionError!.takeRetainedValue() as Error
    }

    print(cipherText)
}

func decrypt() throws {
    let stringKey:NSData = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0mFR0kh0bRuiSTzEW+JE4eIqGkmRiKKJFKThwaFkEg3XWFi97/TZlFl4k9AsoIyrP2fZKsHTlgOsESzr1lh+ndE/ZJR8y1aesAEHbfXCsRoq7MXJV3wFl7qMvDpQgw5Cp7HNlyCZeLgmuJP9ze/Sm4KCgeJwuC7yx6Gy5Q8wHo6FuwVr/5DQbSYqDdoEapVIBo1v4skr7cJC85jBOzSmZFooryRqxZBog+0F68FOTSNviziVWRZFACwm330Dy//6EFBugMURer+jt/GJTW/KNKZI3M55lXlqam+9NnQ8VCr9xzwq3jZgZ7rNkkVddTuhNJ9WjBZJ3aGaQcKgdJaxlAgMBAAECggEACdIu5Zf8lTsp8qrz1Hf4DcYgYmkbpGrpSDGRrCtAjApKIjZapreCecTZodDTun8PGPaVekJWWRTFsxoUPr7HQegFX52xMKaF08i/zBvqQLQlX0C1Iw5ZmR19KlX0oEOCXntJW0OjMoP2as4ARlt7VofotFFazPY+cL5fC0QwmEmqJOMU7fh46oMMkWaW2anjVpFKD0xOxnLIBvYw+wJxUexPoxumRCZ1o62e1wAcLhVqpxAVu3xRdoQj7bunNNvIeuvzP+aPg7twk+VJ7XuPdtTlTSTgIlqnH9T3r/+FTgu0eE3vf4hf56Qvloxwil1rjjRiO91Zixk2uliXGFA9AQKBgQD3GxgPts8PMElxvKkAshMcHu+LPaOLOw4yrkR6H8SyHkFMrUDMyJwUzbmtqgRLzeaTKUTd4W0tnalLNc+LS3CkT6iNywfnkLnyGPJdmGfU8a1VmwJ5ViytKpufcjL8J2xk0dONB49PTz0MOwak19HcNW5i0+IVLec1RmG3jApldQKBgQC7GGLFUeOEqu5ZZAao5vAXZayWXO9IURSdJE735KjEKtdtOxBk6Lqs9xSTMmXpcfJ28s2ahaZT6mIkqJeLGELUaGTKLhmaIDVwak9rGoYBR0Z/sii4gjVOcbfxQp5Fq8ATqCRN4yUc1L5ZrWsoaFQcNZwcTwZegw61fKzcY8IdMQKBgQDtyfrblFWpDhR/CMexTauqEfL+ETDCGPBOwPhQdSW5Kfg4qziCWEIqmtYlUkiUEe3ii4Ij6nw/u0pgf8XbC5u34pgW6twOcG0flfYVL0Yr2u2sRWCaFt0jPq9HPoPltqJOWsSzICU+csgSbvKzyqO+vwQcwjz7mJK356S9FlwiyQKBgQCxLjbbhuFgEkAGh6OG8K0Oe0smWnyAj8hlyKzANdtdavT4jKbJ6Vxf0N27SE8a9L4Zx3bOjyMk9SJcdQyOvTsMzJAmp+G3BCmXfBYZ59RgAY/R/nZxxV2WHDuGTwB0GmKcOu+mkyXiV9asNn70lViSHnLonC3780FKvfc1/4MlAQKBgC0elctcQ9KsKOU0iiLRQbQEEnLLDtesPC2NFOwaCZK7e93PILhjN5PrCCtjFzpP5V3Y0hKggkbXP4zJt2Y3sNJi0LfZaoC0Yf0GGgNHSSNYsEqAh5L9Ob1ynVFC0t+kCfhb4SuPscCyVwdrv7nk09MEqzp0oLoj0gwnIfGbo8L1".data(using: String.Encoding.utf8)! as NSData
    var error: UnsafeMutablePointer<Unmanaged<CFError>?>?
    var key = SecKeyCreateFromData(stringKey as! CFDictionary, [
                            kSecAttrKeyType: kSecAttrKeyTypeRSA,
                            kSecAttrKeyClass: kSecAttrKeyClassPublic,
                            kSecAttrKeySizeInBits: NSNumber(value: 2048),
                                    kSecReturnPersistentRef: true as NSObject] as! CFData, error)
    
    let algorithm:SecKeyAlgorithm = .rsaEncryptionPKCS1
    
    guard let plainText = SecKeyCreateDecryptedData(key, algorithm, "INaW7G3YqiXOIv05fo6dIXo0M2I35Z+LF3euEgIDkVWP4cPwFZjagggOWaGtmj8OqJRSPYjL1iHK2J9AZcfc+P4UlvEeLExa0JoU1mxDzzEFQdZ08MfVRJnlrFeM7FJVnAyxsJDNM8iq9ZbSuSERNcpiAnrNIOjn9eiVj/XbAs1gjZQIziVsEYgCCGYZn/2bSLzolH0o6j3jt21UsaLlSh0XSQGpByBxYYvLcqRxqycTSIs1YewdCGM1bnFXKnVzpEEh9RmEjWDEoh3nPxbNjKmKH8ipxNgk21shuBsv6JVv0VQ65cowNaOUwyXRIFEssCPNMeHgxZTjEXQcmcyHFw==", ) else {
        throw error.takeRetainedValue() as Error
    }
}
