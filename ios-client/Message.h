//
//  Message.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import <Foundation/Foundation.h>

@interface Message : NSObject

@property (atomic, retain) NSString* key;
@property (atomic, retain) NSString* data;

- (id) init: (NSString*) key data: (NSString*) data;

@end
