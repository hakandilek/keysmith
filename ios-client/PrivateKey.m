//
//  PrivateKey.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "PrivateKey.h"

@implementation PrivateKey

@synthesize keyData;

- (id)init: (NSData*) data {
    self = [super init];
    if (self) {
        keyData = [data retain];
    }
    return self;
}

- (void)dealloc {
    [keyData release];
    [super dealloc];
}

@end
