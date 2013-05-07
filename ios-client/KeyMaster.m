//
//  KeyMaster.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "KeyMaster.h"
#import "Base64.h"

@implementation KeyMaster

+(NSData*) publicKeyTag {
    static NSData* pubKey = nil;
    if (pubKey == nil) {
        pubKey = [[NSData alloc] initWithBytes:publicKeyIdentifier length:sizeof(publicKeyIdentifier)];
    }
    return pubKey;
}

+(NSData*) privateKeyTag {
    static NSData* priKey = nil;
    if (priKey == nil) {
        priKey = [[NSData alloc] initWithBytes:privateKeyIdentifier length:sizeof(privateKeyIdentifier)];
    }
    return priKey;
}

+(NSData*) symmetricKeyTag {
    static NSData* symKey = nil;
    if (symKey == nil) {
        symKey = [[NSData alloc] initWithBytes:symmetricKeyIdentifier length:sizeof(symmetricKeyIdentifier)];
    }
    return symKey;
}

+(SecKeyRef) publicKeyRef {
    static SecKeyRef pubKey = nil;
    if (pubKey == nil) {
        OSStatus sanityCheck = noErr;
        NSData* publicTag = KeyMaster.publicKeyTag;
		NSMutableDictionary * queryPublicKey = [[NSMutableDictionary alloc] init];
		
		// Set the public key query dictionary.
		[queryPublicKey setObject:(id)kSecClassKey forKey:(id)kSecClass];
		[queryPublicKey setObject:publicTag forKey:(id)kSecAttrApplicationTag];
		[queryPublicKey setObject:(id)kSecAttrKeyTypeRSA forKey:(id)kSecAttrKeyType];
		[queryPublicKey setObject:[NSNumber numberWithBool:YES] forKey:(id)kSecReturnRef];
		
		// Get the key.
		sanityCheck = SecItemCopyMatching((CFDictionaryRef)queryPublicKey, (CFTypeRef *)&pubKey);
		
		if (sanityCheck != noErr)
		{
			pubKey = NULL;
		}
		
		[queryPublicKey release];
    }
    return pubKey;
}

+(SecKeyRef) privateKeyRef {
    static SecKeyRef priKey = nil;
    if (priKey == nil) {
        OSStatus sanityCheck = noErr;
        NSData* privateTag = KeyMaster.privateKeyTag;
		NSMutableDictionary * queryPrivateKey = [[NSMutableDictionary alloc] init];
		
		// Set the private key query dictionary.
		[queryPrivateKey setObject:(id)kSecClassKey forKey:(id)kSecClass];
		[queryPrivateKey setObject:privateTag forKey:(id)kSecAttrApplicationTag];
		[queryPrivateKey setObject:(id)kSecAttrKeyTypeRSA forKey:(id)kSecAttrKeyType];
		[queryPrivateKey setObject:[NSNumber numberWithBool:YES] forKey:(id)kSecReturnRef];
		
		// Get the key.
		sanityCheck = SecItemCopyMatching((CFDictionaryRef)queryPrivateKey, (CFTypeRef *)&priKey);
		
		if (sanityCheck != noErr)
		{
			priKey = NULL;
		}
		
		[queryPrivateKey release];
    }
    return priKey;
}

+ (PublicKey*) decodePublicKey:(NSString*) data {
    NSData *keyData = [NSData dataWithBase64EncodedString:data];
    PublicKey *key = [[PublicKey alloc] init:keyData andRef:nil];
    [key autorelease];
    return key;
}

+ (NSString*) encodePublicKey:(PublicKey*) key {
    NSString *encoded = [key.keyData base64EncodedString];
    return encoded;
}

+ (KeyPair*) generateKeyPair {
	SecKeyRef publicKeyRef = NULL;
	SecKeyRef privateKeyRef = NULL;
    OSStatus sanityCheck = noErr;
    int keySize = PUBLIC_KEY_SIZE;
    NSData* privateTag  = KeyMaster.privateKeyTag;
    NSData* publicTag = KeyMaster.publicKeyTag;
    
    LOGGING_FACILITY1( keySize == 512 || keySize == 1024 || keySize == 2048, @"%d is an invalid and unsupported key size.", keySize );
    
    // First delete current keys.
    //[self deleteAsymmetricKeys];
    
    // Container dictionaries.
    NSMutableDictionary * privateKeyAttr = [[NSMutableDictionary alloc] init];
    NSMutableDictionary * publicKeyAttr = [[NSMutableDictionary alloc] init];
    NSMutableDictionary * keyPairAttr = [[NSMutableDictionary alloc] init];
    
    // Set top level dictionary for the keypair.
    [keyPairAttr setObject:(id)kSecAttrKeyTypeRSA forKey:(id)kSecAttrKeyType];
    [keyPairAttr setObject:[NSNumber numberWithUnsignedInteger:keySize] forKey:(id)kSecAttrKeySizeInBits];
    
    // Set the private key dictionary.
    [privateKeyAttr setObject:[NSNumber numberWithBool:YES] forKey:(id)kSecAttrIsPermanent];
    [privateKeyAttr setObject:privateTag forKey:(id)kSecAttrApplicationTag];
    // See SecKey.h to set other flag values.
    
    // Set the public key dictionary.
    [publicKeyAttr setObject:[NSNumber numberWithBool:YES] forKey:(id)kSecAttrIsPermanent];
    [publicKeyAttr setObject:publicTag forKey:(id)kSecAttrApplicationTag];
    // See SecKey.h to set other flag values.
    
    // Set attributes to top level dictionary.
    [keyPairAttr setObject:privateKeyAttr forKey:(id)kSecPrivateKeyAttrs];
    [keyPairAttr setObject:publicKeyAttr forKey:(id)kSecPublicKeyAttrs];
    
    // SecKeyGeneratePair returns the SecKeyRefs just for educational purposes.
    sanityCheck = SecKeyGeneratePair((CFDictionaryRef)keyPairAttr, &publicKeyRef, &privateKeyRef);
    LOGGING_FACILITY( sanityCheck == noErr && publicKeyRef != NULL && privateKeyRef != NULL, @"Something really bad went wrong with generating the key pair." );
    
    [privateKeyAttr release];
    [publicKeyAttr release];
    [keyPairAttr release];
    PublicKey *publicKey = [[KeyMaster getPublicKey] retain];
    PrivateKey *privateKey = [[PrivateKey alloc]init:KeyMaster.privateKeyRef];
    KeyPair *kp = [[KeyPair alloc]init:publicKey with:privateKey];
    [kp autorelease];
    [publicKey autorelease];
    return kp;
}

+ (PublicKey *) getPublicKey {
	OSStatus sanityCheck = noErr;
	NSData * publicKeyBits = nil;
    NSData* publicTag = KeyMaster.publicKeyTag;

	NSMutableDictionary * queryPublicKey = [[NSMutableDictionary alloc] init];
    
	// Set the public key query dictionary.
	[queryPublicKey setObject:(id)kSecClassKey forKey:(id)kSecClass];
	[queryPublicKey setObject:publicTag forKey:(id)kSecAttrApplicationTag];
	[queryPublicKey setObject:(id)kSecAttrKeyTypeRSA forKey:(id)kSecAttrKeyType];
	[queryPublicKey setObject:[NSNumber numberWithBool:YES] forKey:(id)kSecReturnData];
    
	// Get the key bits.
	sanityCheck = SecItemCopyMatching((CFDictionaryRef)queryPublicKey, (CFTypeRef *)&publicKeyBits);
    
	if (sanityCheck != noErr) {
        [queryPublicKey release];
        return nil;
	}
    
	[queryPublicKey release];
	PublicKey *pk = [[PublicKey alloc]init:publicKeyBits andRef:KeyMaster.publicKeyRef];
    [pk autorelease];
	return pk;
}


/*
+ (PrivateKey*) loadPrivateKey:(NSString*) keyId {
    
}

+ (NSString*) savePrivateKey:(PrivateKey*) privKey forKeyId:(NSString*) keyId {
    
}
 */


@end
