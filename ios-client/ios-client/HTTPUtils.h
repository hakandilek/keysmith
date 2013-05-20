//
//  HTTPUtils.h
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import <Foundation/Foundation.h>

@interface HTTPUtils : NSObject
+ (NSString*) post: (NSString*) url withJSON: (NSString *) json;
+ (NSString*) get: (NSString*) url;
+ (NSString*) del: (NSString*) url;
@end
