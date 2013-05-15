//
//  Cryptographer.h
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
#import "KeyMaster.h"
#import "Message.h"

@interface Cryptographer : NSObject

- (NSString*) hybridDecrypt: (Message*) crypted withKey: (PrivateKey*) key;
- (Message*)  hybridEncrypt: (NSString*) message withKey: (PublicKey*) key;
- (NSString*) publicDecrypt: (Message*) crypted withKey: (PrivateKey*) key;
- (Message*)  publicEncrypt: (NSString*) message withKey: (PublicKey*) key;
- (NSString*) symmetricDecrypt: (Message*) crypted;
- (Message*)  symmetricEncrypt: (NSString*) message withKey: (SecretKey*) key;

@end
