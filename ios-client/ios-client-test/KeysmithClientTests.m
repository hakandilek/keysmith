//
//  KeysmithClientTests.m
//  Keysmith
//
//  Created by Hakan D on 18/05/2013.
//  Copyright (c) 2013 Mobil Rapor. All rights reserved.
//

#import "KeysmithClientTests.h"
#import "KeysmithClient.h"
#import "PublicKey.h"

@implementation KeysmithClientTests

KeysmithClient* client;

- (void) setUp {
    [super setUp];
    client = [[KeysmithClient alloc] initWithBaseURL:@"http://api.mobilrapor.com.tr/keysmith"];
}

- (void) tearDown {
    [client release];
    [super tearDown];
}

- (void) testPostGetRemovePublicKey {
    NSData *pkData = [@"test" dataUsingEncoding:NSUTF8StringEncoding];
    SecKeyRef pkRef = nil;
    PublicKey *pk = [[PublicKey alloc] init:pkData andRef:pkRef];
    
    // post
    NSString *pkId = [client postPublicKey:pk];
    STAssertNotNil(pkId, @"should not be null");
    
    // get
    PublicKey *pk2 = [client getPublicKey:pkId];
    STAssertNotNil(pk2, @"should not be null");
    
    // remove
    PublicKey *pk3 = [client removePublicKey:pkId];
    STAssertNotNil(pk3, @"should not be null");
    
    // get again
    PublicKey *pk4 = [client getPublicKey:pkId];
    STAssertNil(pk4, @"should be null");
}

- (void) testPostAndGet {
    NSData *pkData = [@"test" dataUsingEncoding:NSUTF8StringEncoding];
    SecKeyRef pkRef = nil;
    PublicKey *pubKey = [[PublicKey alloc] init:pkData andRef:pkRef];
    
    NSString *keyId = [client postPublicKey:pubKey];
    STAssertNotNil(keyId, @"keyId should not be null");
    
    PublicKey *pubKey2 = [client getPublicKey:keyId];
    STAssertNotNil(pubKey2, @"returned key should not be null");
    STAssertNotNil(pubKey2.keyData, @"key should have data");
    NSString *outputString = [[NSString alloc] initWithData:pubKey2.keyData encoding:NSUTF8StringEncoding];
    log(@"output: %@", outputString);
    STAssertEqualObjects(@"test", outputString, @"key contents should be equal");
}

- (void) testPostUpdateAndGet {
    NSData *pkData = [@"test" dataUsingEncoding:NSUTF8StringEncoding];
    SecKeyRef pkRef = nil;
    PublicKey *pubKey = [[PublicKey alloc] init:pkData andRef:pkRef];
    
    NSString *keyId = [client postPublicKey:pubKey];
    STAssertNotNil(keyId, @"keyId should not be null");
    
    PublicKey *pubKey2 = [[PublicKey alloc] init:[@"test2" dataUsingEncoding:NSUTF8StringEncoding] andRef:nil];
    [client updatePublicKey:pubKey2 forId:keyId];
    
    PublicKey *pubKeyTest = [client getPublicKey:keyId];
    STAssertNotNil(pubKeyTest, @"returned key should not be null");
    STAssertNotNil(pubKeyTest.keyData, @"key should have data");
    NSString *outputString = [[NSString alloc] initWithData:pubKeyTest.keyData encoding:NSUTF8StringEncoding];
    log(@"output: %@", outputString);
    STAssertEqualObjects(@"test2", outputString, @"key contents should be equal");
}

- (void) testPostRemoveAndGet {
    NSData *pkData = [@"test" dataUsingEncoding:NSUTF8StringEncoding];
    SecKeyRef pkRef = nil;
    PublicKey *pubKey = [[PublicKey alloc] init:pkData andRef:pkRef];
    
    NSString *keyId = [client postPublicKey:pubKey];
    STAssertNotNil(keyId, @"keyId should not be null");
    
    PublicKey *pubKey2 = [client removePublicKey:keyId];
    STAssertNotNil(pubKey2, @"returned key should not be null");
    STAssertNotNil(pubKey2.keyData, @"key should have data");
    NSString *outputString = [[NSString alloc] initWithData:pubKey2.keyData encoding:NSUTF8StringEncoding];
    log(@"output: %@", outputString);
    STAssertEqualObjects(@"test", outputString, @"key contents should be equal");
    
    PublicKey *pubKeyTest = [client getPublicKey:keyId];
    log(@"key: %@", pubKeyTest);
    STAssertNil(pubKeyTest, @"key should be deleted");
}



@end
