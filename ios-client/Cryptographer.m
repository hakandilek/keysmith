//
//  Cryptographer.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "Cryptographer.h"

@implementation Cryptographer

- (NSString*) hybridDecrypt: (Message*) crypted withKey: (PrivateKey*) key {
	NSString* secretKeyData = [self decrypt:crypted.key withKey: key];
	SecretKey* secretKey = [KeyMaster decodeSecretKey:secretKeyData];
	NSString* message = [self decrypt:crypted.data withKey:secretKey];
	return message;
}

- (Message*) hybridEncrypt: (NSString*) message withKey: (PublicKey*) key {
	SecretKey* secretKey = [KeyMaster generateSecretKey];
	NSString* encryptedMsg = [self encrypt:message withKey:secretKey];
	NSString* encryptedKey = [self encrypt:secretKey withKey:key];
	Message* message = [[Message alloc] init:encryptedKey data:encryptedMsg];
	[message autorelease];
	return message;
}

- (NSString*) publicDecrypt: (Message*) crypted withKey: (PrivateKey*) key {
	NSString* message = [self decrypt:crypted.data withKey:key];
	return message;
}

- (Message*) publicEncrypt: (NSString*) message withKey: (PublicKey*) key {
	NSString* encryptedMsg = [self encrypt:message withKey:key];
	Message* message = [[Message alloc] init:nil data:encryptedMsg];
	[message autorelease];
	return message;
}

- (NSString*) symmetricDecrypt: (Message*) crypted {
	SecretKey* secretKey = [KeyMaster decodeSecretKey:crypted.key];
	NSString* message = [self decrypt:crypted.data withKey:key];
	return message;
}

- (Message*) symmetricEncrypt: (NSString*) message withKey: (SecretKey*) key {
	NSString* encryptedMsg = [self encrypt:message withKey:key];
	NSString* encryptedKey = [KeyMaster encodeSecretKey:key];
	Message* message = [[Message alloc] init:encryptedKey data:encryptedMsg];
	[message autorelease];
	return message;
}

//------------- private methods ----------------

- (NSString*) decrypt: (NSString*) encrypted withKey: (PrivateKey*) pk {
	// TODO:
}

- (NSString*) encrypt: (SecretKey*) secretKey withKey: (PublicKey*) key {
	// TODO:
}

- (NSString*) encrypt: (NSString*) data withKey: (PublicKey*) pk {
	// TODO:
}

- (NSString*) encrypt: (NSString*) data withKey: (SecretKey*) key {
	// TODO:
}

- (NSString*) decrypt: (NSString*) encrypted withKey: (SecretKey*) key {
	// TODO:
}

- (NSString*) toUTF8String: (NSData*) bytes {
	// TODO:
}

@end
