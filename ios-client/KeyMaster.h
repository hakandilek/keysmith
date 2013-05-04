//
//  KeyMaster.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import <Foundation/Foundation.h>
#import "PublicKey.h"
#import "PrivateKey.h"
#import "Base64.h"

@interface KeyMaster : NSObject

+ (PublicKey*) decodePublicKey:(NSString*) data;
+ (NSString*) encodePublicKey:(PublicKey*) data;

@end
