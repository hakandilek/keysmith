//
//  KeyPair.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import <Foundation/Foundation.h>
#import "PublicKey.h"
#import "PrivateKey.h"

@interface KeyPair : NSObject

@property (atomic, retain) PublicKey *publicKey;
@property (atomic, retain) PrivateKey *privateKey;

- (id)init: (PublicKey*) publicKey with: (PrivateKey*) privateKey;

@end
