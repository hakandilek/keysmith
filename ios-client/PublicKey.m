//
//  PublicKey.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "PublicKey.h"

@implementation PublicKey

@synthesize keyData;
@synthesize keyRef;

- (id)init: (NSData*) data andRef:(SecKeyRef) kr {
    self = [super init];
    if (self) {
        keyData = [data retain];
        keyRef = kr;
    }
    return self;
}

- (void)dealloc {
    [keyData release];
    [super dealloc];
}
@end
