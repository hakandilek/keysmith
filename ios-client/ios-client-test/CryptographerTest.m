//
//  CryptographerTest.m
//  Keysmith
//
//  Created by Hakan D on 18/05/2013.
//  Copyright (c) 2013 Mobil Rapor. All rights reserved.
//

#import "CryptographerTest.h"
#import "Cryptographer.h"
#import "PublicKey.h"
#import "PrivateKey.h"
#import "SecretKey.h"
#import "KeyPair.h"
#import "KeyMaster.h"
#import "Message.h"

@implementation CryptographerTest

Cryptographer *c;

- (void) setUp {
    [super setUp];
    c = [[Cryptographer alloc] init];
}

- (void) tearDown {
    [c release];
    [super tearDown];
}

- (void) testPublicEncryptDecrypt {
    KeyPair *kp = [KeyMaster generateKeyPair];
    
    Message *msg = [c publicEncrypt:@"test" withKey:kp.publicKey];
    STAssertNotNil(msg, @"not null");
    STAssertNotNil(msg.data, @"not null");
    STAssertNil(msg.key, @"is null");
    
    NSString *test = [c publicDecrypt:msg withKey:kp.privateKey];
    STAssertNotNil(test, @"not null");
    STAssertEqualObjects(@"test", test, @"is equal");
}

- (void) testSymmetricEncryptDecrypt {
    SecretKey *sk = [KeyMaster generateSecretKey];
    
    Message *msg = [c symmetricEncrypt:@"test" withKey:sk];
    STAssertNotNil(msg, @"not null");
    STAssertNotNil(msg.data, @"not null");
    STAssertNotNil(msg.key, @"not null");
    
    NSString *test = [c symmetricDecrypt:msg];
    STAssertNotNil(test, @"not null");
    STAssertEqualObjects(@"test", test, @"is equal");    
}

- (void) testHybridEncryptDecrypt {
    KeyPair *kp = [KeyMaster generateKeyPair];
    
    Message *msg = [c hybridEncrypt:@"test" withKey:kp.publicKey];
    STAssertNotNil(msg, @"not null");
    STAssertNotNil(msg.data, @"not null");
    STAssertNotNil(msg.key, @"is null");
    
    NSString *test = [c hybridDecrypt:msg withKey:kp.privateKey];
    STAssertNotNil(test, @"not null");
    STAssertEqualObjects(@"test", test, @"is equal");
}

- (void) testHybridDecryptDotNetMessage {
    KeyPair *kp = [KeyMaster generateKeyPair];
    NSString *msgKey = @"lij/8cKCOnBmuEO1wmJZR2kraaJGGqxeGcIK5dDIrnNcbwdtOPEaog5fwKP9H0Chz5k5MO6LJ3N5tE7q0IrSFOKJKSio0ale3A99hMxdfZQvLruC6eEPKjumXgYwD0hAb+Guvwi09JJsLzUh5R+Ayr3cn69pxsCa5pd07AifLskO+SpWL1xd0v3oZgrNWmomHJdKLsD6k9FMB2LkaEKWq1ld0ZS4t6coBLGsw9Nh194xPvA8MHOJBIVDg5axMistOWMWBU+wNqhCaTeZBCDmNR+/1vZL2BsvmDI5tQOUo6Igv8Sd06fdZBW6eX32AcYIKT7N9gJa+ama5eiY3cUNcQ==";
    NSString *msgData = @"Gy/IQf8KagU=";
    
    Message *msg = [[Message alloc] init:msgKey data:msgData];
    NSString *test = [c hybridDecrypt:msg withKey:kp.privateKey];
    STAssertNotNil(test, @"not null");
    STAssertEqualObjects(@"test", test, @"is equal");
}


@end
