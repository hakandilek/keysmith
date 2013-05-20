//
//  KeysmithTests.m
//  KeysmithTests
//
//  Created by Hakan D on 18/05/2013.
//  Copyright (c) 2013 Mobil Rapor. All rights reserved.
//

#import "KeyMasterTests.h"
#import "KeyMaster.h"
#import "PublicKey.h"
#import "SecretKey.h"

@implementation KeyMasterTests

- (void) setUp {
    [super setUp];
}

- (void) tearDown {
    [super tearDown];
}

- (void) testEncodePublicKey {
    NSData *pkData = [@"test" dataUsingEncoding:NSUTF8StringEncoding];
    SecKeyRef pkRef = nil;
    PublicKey *pk = [[PublicKey alloc] init:pkData andRef:pkRef];
    NSString *encoded = [KeyMaster encodePublicKey:pk];
    STAssertNotNil(encoded, @"should not be null");
}

- (void) testDecodePublicKey {
    PublicKey *pk = [KeyMaster decodePublicKey:@"dGVzdA=="];
    STAssertNotNil(pk, @"should not be null");
}

- (void) testEncodeDecorePublicKey {
    NSData *pkData = [@"test" dataUsingEncoding:NSUTF8StringEncoding];
    SecKeyRef pkRef = nil;
    PublicKey *pk = [[PublicKey alloc] init:pkData andRef:pkRef];
    
    NSString *encoded = [KeyMaster encodePublicKey:pk];
    PublicKey *pk2 = [KeyMaster decodePublicKey:encoded];
    STAssertNotNil(pk2, @"should not be null");
    STAssertNotNil(pk2.keyData, @"should not be null");
    STAssertTrue([pkData isEqualToData:pk2.keyData], @"should be equal");
}

- (void) testGeneratePublicKey {
    KeyPair *kp = [KeyMaster generateKeyPair];
    STAssertNotNil(kp, @"not null");
    STAssertNotNil(kp.privateKey, @"not null");
    STAssertNotNil(kp.publicKey, @"not null");
    STAssertNotNil(kp.publicKey.keyData, @"not null");
}

- (void) testEncodeGeneratedPublicKey {
    KeyPair *kp = [KeyMaster generateKeyPair];
    STAssertNotNil(kp, @"not null");
    STAssertNotNil(kp.publicKey, @"not null");
    
    NSString* encoded = [KeyMaster encodePublicKey:kp.publicKey];
    STAssertNotNil(encoded, @"encoded pk");
    log(@"encoded pk: %@", encoded);
}

- (void) testEncodeSecretKey {
    NSData *pkData = [@"test" dataUsingEncoding:NSUTF8StringEncoding];
    SecretKey *sk = [[SecretKey alloc] init:pkData];
    NSString *encoded = [KeyMaster encodeSecretKey:sk];
    STAssertNotNil(encoded, @"should not be null");
}

- (void) testDecodeSecretKey {
    SecretKey *pk = [KeyMaster decodeSecretKey:@"dGVzdA=="];
    STAssertNotNil(pk, @"should not be null");
}

- (void) testEncodeDecoreSecretKey {
    NSData *pkData = [@"test" dataUsingEncoding:NSUTF8StringEncoding];
    SecretKey *sk = [[SecretKey alloc] init:pkData];
    
    NSString *encoded = [KeyMaster encodeSecretKey:sk];
    SecretKey *sk2 = [KeyMaster decodeSecretKey:encoded];
    STAssertNotNil(sk2, @"should not be null");
    STAssertNotNil(sk2.keyData, @"should not be null");
    STAssertTrue([pkData isEqualToData:sk2.keyData], @"should be equal");
}

- (void) testGenerateSecretKey {
    SecretKey *sk = [KeyMaster generateSecretKey];
    STAssertNotNil(sk, @"not null");
    STAssertNotNil(sk.keyData, @"not null");
}

@end
