//
//  PrivateKey.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "PrivateKey.h"

@implementation PrivateKey

@synthesize keyRef;

- (id)init:(SecKeyRef) kr {
    self = [super init];
    if (self) {
        keyRef = kr;
    }
    return self;
}

- (void)dealloc {
    
    [super dealloc];
}

@end
