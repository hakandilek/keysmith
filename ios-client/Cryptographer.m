//
//  Cryptographer.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "Cryptographer.h"

@implementation Cryptographer


//------------- private methods ----------------

- (NSString*) decrypt: (NSString*) encrypted withPrivateKey: (PrivateKey*) pk {
	// TODO:
    return nil;
}

- (NSString*) encryptSecretKey: (SecretKey*) secretKey withPublicKey: (PublicKey*) key {
	// TODO:
    return nil;
}

- (NSString*) encrypt: (NSString*) data withPublicKey: (PublicKey*) pk {
	// TODO:
    return nil;
}

- (NSString*) encrypt: (NSString*) data withSecretKey: (SecretKey*) key {
	// TODO:
    return nil;
}

- (NSString*) decrypt: (NSString*) encrypted withSecretKey: (SecretKey*) key {
	// TODO:
    return nil;
}

- (NSString*) toUTF8String: (NSData*) bytes {
	// TODO:
    return nil;
}

//------------- public  methods ----------------

- (NSString*) hybridDecrypt: (Message*) crypted withKey: (PrivateKey*) key {
	NSString* secretKeyData = [self decrypt:crypted.key withPrivateKey: key];
	SecretKey* secretKey = [KeyMaster decodeSecretKey:secretKeyData];
	NSString* message = [self decrypt:crypted.data withSecretKey:secretKey];
	return message;
}

- (Message*) hybridEncrypt: (NSString*) message withKey: (PublicKey*) key {
	SecretKey* secretKey = [KeyMaster generateSecretKey];
	NSString* encryptedMsg = [self encrypt:message withSecretKey:secretKey];
	NSString* encryptedKey = [self encryptSecretKey:secretKey withPublicKey:key];
	Message* msg = [[Message alloc] init:encryptedKey data:encryptedMsg];
	[msg autorelease];
	return msg;
}

- (NSString*) publicDecrypt: (Message*) crypted withKey: (PrivateKey*) key {
	NSString* message = [self decrypt:crypted.data withPrivateKey:key];
	return message;
}

- (Message*) publicEncrypt: (NSString*) message withKey: (PublicKey*) key {
	NSString* encryptedMsg = [self encrypt:message withPublicKey:key];
	Message* msg = [[Message alloc] init:nil data:encryptedMsg];
	[msg autorelease];
	return msg;
}

- (NSString*) symmetricDecrypt: (Message*) crypted {
	SecretKey* secretKey = [KeyMaster decodeSecretKey:crypted.key];
	NSString* message = [self decrypt:crypted.data withSecretKey:secretKey];
	return message;
}

- (Message*) symmetricEncrypt: (NSString*) message withKey: (SecretKey*) key {
	NSString* encryptedMsg = [self encrypt:message withSecretKey:key];
	NSString* encryptedKey = [KeyMaster encodeSecretKey:key];
	Message* msg = [[Message alloc] init:encryptedKey data:encryptedMsg];
	[msg autorelease];
	return msg;
}

@end
