//
//  KeyMaster.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import <Foundation/Foundation.h>
#import <Security/Security.h>
#import "KeysmithCommon.h"
#import "Base64.h"
#import "PrivateKey.h"
#import "PublicKey.h"
#import "KeyPair.h"
#import "SecretKey.h"

static const uint8_t publicKeyIdentifier[]		= kPublicKeyTag;
static const uint8_t privateKeyIdentifier[]		= kPrivateKeyTag;
static const uint8_t secretKeyIdentifier[]      = kSecretKeyTag;

enum {
	CSSM_ALGID_NONE =					0x00000000L,
	CSSM_ALGID_VENDOR_DEFINED =			CSSM_ALGID_NONE + 0x80000000L,
    CSSM_ALGID_3DES_3KEY_EDE =			CSSM_ALGID_NONE + 17,
    CSSM_ALGID_3DES_3KEY =           	CSSM_ALGID_3DES_3KEY_EDE
};

@interface KeyMaster : NSObject

+ (NSData*) publicKeyTag;
+ (NSData*) privateKeyTag;
+ (NSData*) secretKeyTag;
+ (SecKeyRef) publicKeyRef;
+ (SecKeyRef) privateKeyRef;

+ (PublicKey*) getPublicKey;

+ (KeyPair*) getKeyPair;
+ (KeyPair*) generateKeyPair;
+ (KeyPair*) generateKeyPairSize: (int) size;
+ (PublicKey*) decodePublicKey:(NSString*) data;
+ (NSString*) encodePublicKey:(PublicKey*) data;

+ (SecretKey*) generateSecretKey;
+ (SecretKey*) decodeSecretKey:(NSString*) data;
+ (NSString*) encodeSecretKey:(SecretKey*) data;

@end
