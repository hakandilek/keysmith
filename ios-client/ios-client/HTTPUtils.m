
//
//  HTTPUtils.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "HTTPUtils.h"

@implementation HTTPUtils

+ (NSString*) post: (NSString*) url withJSON: (NSString *) json {
    
    NSString *result;
    NSData *requestData, *responseData;
    
    @try {
        requestData = [NSData dataWithBytes:[json UTF8String] length:[json length]];
        [requestData retain];
        
        NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString: url]];
        [request retain];
        [request setHTTPMethod:@"POST"];
        [request setValue:@"application/json" forHTTPHeaderField:@"Accept"];
        [request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
        [request setValue:[NSString stringWithFormat:@"%d", [requestData length]] forHTTPHeaderField:@"Content-Length"];
        [request setHTTPBody: requestData];
        
        responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
        [request release];
        [responseData retain];
        result = [[[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding] autorelease];
    } @catch (NSException *e) {
        //TODO: handle exception
    } @finally {
        [requestData autorelease];
        [responseData autorelease];
    }
    return result;
}

+ (NSString*) get: (NSString*) url {
    
    NSString *result;
    NSData *responseData;
    
    @try {
        NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString: url]];
        [request retain];
        [request setHTTPMethod:@"GET"];
        
        responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
        [request release];
        [responseData retain];
        result = [[[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding] autorelease];
        
    } @catch (NSException *e) {
        //TODO: handle exception
    } @finally {
        [responseData autorelease];
    }
    return result;
}

+ (NSString*) del: (NSString*) url {
    NSString *result;
    NSData *responseData;
    
    @try {
        NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString: url]];
        [request retain];
        [request setHTTPMethod:@"DELETE"];
        
        responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
        [request release];
        [responseData retain];
        result = [[[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding] autorelease];
        
    } @catch (NSException *e) {
        //TODO: handle exception
    } @finally {
        [responseData autorelease];
    }
    return result; 
}

@end
