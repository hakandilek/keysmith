//
//  KeyMaster.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import <Foundation/Foundation.h>
#import "Base64.h"
#import "PrivateKey.h"
#import "PublicKey.h"
#import "KeyPair.h"
#import "SecretKey.h"

@interface KeyMaster : NSObject

+ (KeyPair*) generateKeyPair;
+ (KeyPair*) generateKeyPairSize: (int) size;
+ (PublicKey*) decodePublicKey:(NSString*) data;
+ (NSString*) encodePublicKey:(PublicKey*) data;

+ (SecretKey*) generateSecretKey;
+ (SecretKey*) decodeSecretKey:(NSString*) data;
+ (NSString*) encodeSecretKey:(SecretKey*) data;

@end
