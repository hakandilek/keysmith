//
//  KeyPair.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "KeyPair.h"

@implementation KeyPair

@synthesize publicKey, privateKey;

- (id) init: (PublicKey*) pubKey with: (PrivateKey*) priKey;{
    self = [super init];
    if (self) {
        publicKey = pubKey;
        privateKey = priKey;
        [publicKey retain];
        [privateKey retain];
    }
    return self;
}

- (void) dealloc {
    [publicKey release];
    [privateKey release];
    [super dealloc];
}

@end
