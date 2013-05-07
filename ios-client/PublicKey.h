//
//  PublicKey.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import <Foundation/Foundation.h>

@interface PublicKey : NSObject

@property (atomic, retain, readonly) NSData *keyData;
@property (atomic, readonly) SecKeyRef keyRef;

- (id)init: (NSData*) data andRef:(SecKeyRef) keyRef;

@end
