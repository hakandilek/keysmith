//
//  KeysmithClient.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "PublicKey.h"
#import "HTTPUtils.h"
#import "Common.h"
#import "KeyMaster.h"

static NSString* GET_URL = @"/publicKey/%@";
static NSString* POST_URL = @"/publicKey";
static NSString* UPDATE_URL = @"/publicKey/%@";
static NSString* REMOVE_URL = @"/publicKey/%@";

@interface KeysmithClient : NSObject

@property (atomic, retain, readonly) NSString *baseURL;

- (id)initWithBaseURL: (NSString*) baseURL;

- (PublicKey*) getPublicKey : (NSString *) keyId;
- (NSString*) postPublicKey : (PublicKey*) publicKey;
- (NSString*) updatePublicKey : (PublicKey*) publicKey forId: (NSString *) keyId;
- (PublicKey*) removePublicKey : (NSString *) keyId;

@end
