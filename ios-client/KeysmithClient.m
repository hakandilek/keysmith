//
//  KeysmithClient.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "KeysmithClient.h"

@implementation KeysmithClient

@synthesize baseURL;

- (id) initWithBaseURL: (NSString*) url {
    self = [super init];
    if (self) {
        baseURL = [url retain];
    }
    return self;
}

- (void) dealloc {
    [baseURL release];
    [super dealloc];
}

- (PublicKey*) getPublicKey : (NSString *) keyId {
    NSString* url;
    NSString *result;
    PublicKey *publicKey;
    log(@"getPublicKey keyId : %@", keyId);
    
    url = [baseURL stringByAppendingFormat:GET_URL, keyId];
    log(@"url : %@", url);
    
    result = [HTTPUtils get:url];
    log(@"result : %@", result);
    
    if (result == (id)[NSNull null] || [result length] == 0 )
        return Nil;
    
    // decode data
    publicKey = [[KeyMaster decodePublicKey:result] retain];
    
    [publicKey autorelease];
    return publicKey;
}

- (NSString*) postPublicKey : (PublicKey*) publicKey {
    NSString* url;
    NSString *result;
    NSString *keyData = [KeyMaster encodePublicKey:publicKey];
    log(@"postPublicKey key : %@", keyData);
    
    url = [baseURL stringByAppendingString:POST_URL];
    log(@"url : %@", url);
    
    result = [HTTPUtils post:url withJSON:keyData];
    [result retain];
    log(@"result : %@", result);
    
    [result autorelease];
    return result;
}

- (NSString*) updatePublicKey : (PublicKey*) publicKey forId: (NSString *) keyId {
    NSString* url;
    NSString *result;
    NSString *keyData = [KeyMaster encodePublicKey:publicKey];
    log(@"updatePublicKey key : %@", keyId);
    log(@"data : %@", keyData);
    
    url = [baseURL stringByAppendingFormat:UPDATE_URL, keyId];
    log(@"url : %@", url);
    
    result = [HTTPUtils post:url withJSON:keyData];
    [result retain];
    log(@"result : %@", result);
    
    [result autorelease];
    return result;
}

- (PublicKey*) removePublicKey : (NSString *) keyId {
    NSString* url;
    NSString *result;
    PublicKey *publicKey;
    log(@"removePublicKey keyId : %@", keyId);
    
    url = [baseURL stringByAppendingFormat:REMOVE_URL, keyId];
    log(@"url : %@", url);
    
    result = [HTTPUtils del:url];
    log(@"result : %@", result);
    
    // decode data
    publicKey = [[KeyMaster decodePublicKey:result] retain];
    
    [publicKey autorelease];
    return publicKey;
}

@end
