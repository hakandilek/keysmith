//
//  Message.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "Message.h"

@implementation Message

@synthesize key, data;

- (id)init: (NSString*) k data: (NSString*) d {
    self = [super init];
    if (self) {
        key = k;
        data = d;
        [data retain];
        [key retain];
    }
    return self;
}

- (void) dealloc {
    [data release];
    [key release];
    [super dealloc];
}

@end
