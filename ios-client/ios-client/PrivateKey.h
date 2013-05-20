//
//  PrivateKey.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import <Foundation/Foundation.h>

@interface PrivateKey : NSObject

@property (atomic, readonly) SecKeyRef keyRef;

- (id)init:(SecKeyRef) keyRef;

@end
