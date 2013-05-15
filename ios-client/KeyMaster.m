//
//  KeyMaster.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "KeyMaster.h"

@implementation KeyMaster

+ (KeyPair*) generateKeyPair {
	return [KeyPair generateKeyPairSize:1024];
}

+ (KeyPair*) generateKeyPairSize: (int) size {
	// TODO:
}

+ (PublicKey*) decodePublicKey:(NSString*) data {
    NSData *keyData = [NSData dataWithBase64EncodedString:data];
    PublicKey *key = [[PublicKey alloc] init:keyData];	
    return key;
}

+ (NSString*) encodePublicKey:(PublicKey*) key {
    NSString *encoded = [key.keyData base64EncodedString];
    return encoded;
}

+ (SecretKey*) generateSecretKey {
	// TODO:
}

+ (SecretKey*) decodeSecretKey:(NSString*) data {
    NSData *keyData = [NSData dataWithBase64EncodedString:data];
    SecretKey *key = [[SecretKey alloc] init:keyData];
    return key;
}

+ (NSString*) encodeSecretKey:(SecretKey*) data {
    NSString *encoded = [key.keyData base64EncodedString];
    return encoded;
}

@end
