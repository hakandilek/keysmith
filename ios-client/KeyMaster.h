//
//  KeyMaster.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import <Foundation/Foundation.h>
#import <Security/Security.h>
#import "Base64.h"
#import "PrivateKey.h"
#import "PublicKey.h"
#import "KeyPair.h"
#import "SecretKey.h"
#import "PublicKey.h"
#import "PrivateKey.h"
#import "KeyPair.h"
#import "KeysmithCommon.h"

static const uint8_t publicKeyIdentifier[]		= kPublicKeyTag;
static const uint8_t privateKeyIdentifier[]		= kPrivateKeyTag;
static const uint8_t symmetricKeyIdentifier[]	= kSymmetricKeyTag;

@interface KeyMaster : NSObject

+ (NSData*) publicKeyTag;
+ (NSData*) privateKeyTag;
+ (NSData*) symmetricKeyTag;
+ (SecKeyRef) publicKeyRef;
+ (SecKeyRef) privateKeyRef;

+ (KeyPair*) generateKeyPair;
+ (KeyPair*) generateKeyPairSize: (int) size;
+ (PublicKey*) decodePublicKey:(NSString*) data;
+ (NSString*) encodePublicKey:(PublicKey*) data;

+ (SecretKey*) generateSecretKey;
+ (SecretKey*) decodeSecretKey:(NSString*) data;
+ (NSString*) encodeSecretKey:(SecretKey*) data;
+ (PublicKey*) getPublicKey;


@end
