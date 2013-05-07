//
//  KeyMaster.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import <Foundation/Foundation.h>
#import "PublicKey.h"
#import "PrivateKey.h"
#import "KeyPair.h"
#import "KeysmithCommon.h"
#import <Security/Security.h>

static int PUBLIC_KEY_SIZE = 2048;
static int PRIVATE_KEY_SIZE = 112;
static const uint8_t publicKeyIdentifier[]		= kPublicKeyTag;
static const uint8_t privateKeyIdentifier[]		= kPrivateKeyTag;
static const uint8_t symmetricKeyIdentifier[]	= kSymmetricKeyTag;

@interface KeyMaster : NSObject

+(NSData*) publicKeyTag;
+(NSData*) privateKeyTag;
+(NSData*) symmetricKeyTag;
+(SecKeyRef) publicKeyRef;
+(SecKeyRef) privateKeyRef;

+ (PublicKey*) decodePublicKey:(NSString*) data;
+ (NSString*) encodePublicKey:(PublicKey*) data;

+ (KeyPair*) generateKeyPair;
+ (PublicKey*) getPublicKey;

@end
