//
//  SecretKey.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import <Foundation/Foundation.h>

@interface SecretKey : NSObject

@property (atomic, retain, readonly) NSData *keyData;

- (id)init: (NSData*) data;

@end
