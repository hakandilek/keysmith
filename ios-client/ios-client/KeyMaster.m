//
//  KeyMaster.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "KeyMaster.h"
#import "Base64.h"

@implementation KeyMaster

static int PUBLIC_KEY_SIZE = 2048;

+(NSData*) publicKeyTag {
    static NSData* pubKey = nil;
    if (pubKey == nil) {
        pubKey = [[NSData alloc] initWithBytes:publicKeyIdentifier length:sizeof(publicKeyIdentifier)];
    }
    return pubKey;
}

+(NSData*) privateKeyTag {
    static NSData* priKey = nil;
    if (priKey == nil) {
        priKey = [[NSData alloc] initWithBytes:privateKeyIdentifier length:sizeof(privateKeyIdentifier)];
    }
    return priKey;
}

+(NSData*) secretKeyTag {
    static NSData* symKey = nil;
    if (symKey == nil) {
        symKey = [[NSData alloc] initWithBytes:secretKeyIdentifier length:sizeof(secretKeyIdentifier)];
    }
    return symKey;
}

+(SecKeyRef) publicKeyRef {
    static SecKeyRef pubKey = nil;
    if (pubKey == nil) {
        OSStatus sanityCheck = noErr;
        NSData* publicTag = KeyMaster.publicKeyTag;
		NSMutableDictionary * queryPublicKey = [[NSMutableDictionary alloc] init];
		
		// Set the public key query dictionary.
		[queryPublicKey setObject:(id)kSecClassKey forKey:(id)kSecClass];
		[queryPublicKey setObject:publicTag forKey:(id)kSecAttrApplicationTag];
		[queryPublicKey setObject:(id)kSecAttrKeyTypeRSA forKey:(id)kSecAttrKeyType];
		[queryPublicKey setObject:[NSNumber numberWithBool:YES] forKey:(id)kSecReturnRef];
		
		// Get the key.
		sanityCheck = SecItemCopyMatching((CFDictionaryRef)queryPublicKey, (CFTypeRef *)&pubKey);
		
		if (sanityCheck != noErr)
		{
			pubKey = NULL;
		}
		
		[queryPublicKey release];
    }
    return pubKey;
}

+(SecKeyRef) privateKeyRef {
    static SecKeyRef priKey = nil;
    if (priKey == nil) {
        OSStatus sanityCheck = noErr;
        NSData* privateTag = KeyMaster.privateKeyTag;
		NSMutableDictionary * queryPrivateKey = [[NSMutableDictionary alloc] init];
		
		// Set the private key query dictionary.
		[queryPrivateKey setObject:(id)kSecClassKey forKey:(id)kSecClass];
		[queryPrivateKey setObject:privateTag forKey:(id)kSecAttrApplicationTag];
		[queryPrivateKey setObject:(id)kSecAttrKeyTypeRSA forKey:(id)kSecAttrKeyType];
		[queryPrivateKey setObject:[NSNumber numberWithBool:YES] forKey:(id)kSecReturnRef];
		
		// Get the key.
		sanityCheck = SecItemCopyMatching((CFDictionaryRef)queryPrivateKey, (CFTypeRef *)&priKey);
		
		if (sanityCheck != noErr)
		{
			priKey = NULL;
		}
		
		[queryPrivateKey release];
    }
    return priKey;
}

+ (PublicKey*) decodePublicKey:(NSString*) data {
    NSData *keyData = [NSData dataWithBase64EncodedString:data];
    PublicKey *key = [[PublicKey alloc] init:keyData andRef:nil];
    [key autorelease];
    return key;
}

+ (NSString*) encodePublicKey:(PublicKey*) key {
    NSString *encoded = [key.keyData base64EncodedString];
    return encoded;
}

+ (KeyPair*) getKeyPair {
    PublicKey *publicKey = [[KeyMaster getPublicKey] retain];
    PrivateKey *privateKey = [[PrivateKey alloc]init:KeyMaster.privateKeyRef];
    KeyPair *kp = [[KeyPair alloc]init:publicKey with:privateKey];
    [kp autorelease];
    [publicKey autorelease];
    [privateKey autorelease];
    return kp;
}

+ (KeyPair*) generateKeyPair {
	return [[self class] generateKeyPairSize:PUBLIC_KEY_SIZE];
}

+ (KeyPair*) generateKeyPairSize: (int) keySize {
	SecKeyRef publicKeyRef = NULL;
	SecKeyRef privateKeyRef = NULL;
    OSStatus sanityCheck = noErr;
    NSData* privateTag  = KeyMaster.privateKeyTag;
    NSData* publicTag = KeyMaster.publicKeyTag;
    
    LOGGING_FACILITY1( keySize == 512 || keySize == 1024 || keySize == 2048, @"%d is an invalid and unsupported key size.", keySize );
    
    // First delete current keys.
    //[self deleteAsecretKeys];
    
    // Container dictionaries.
    NSMutableDictionary * privateKeyAttr = [[NSMutableDictionary alloc] init];
    NSMutableDictionary * publicKeyAttr = [[NSMutableDictionary alloc] init];
    NSMutableDictionary * keyPairAttr = [[NSMutableDictionary alloc] init];
    
    // Set top level dictionary for the keypair.
    [keyPairAttr setObject:(id)kSecAttrKeyTypeRSA forKey:(id)kSecAttrKeyType];
    [keyPairAttr setObject:[NSNumber numberWithUnsignedInteger:keySize] forKey:(id)kSecAttrKeySizeInBits];
    
    // Set the private key dictionary.
    [privateKeyAttr setObject:[NSNumber numberWithBool:YES] forKey:(id)kSecAttrIsPermanent];
    [privateKeyAttr setObject:privateTag forKey:(id)kSecAttrApplicationTag];
    // See SecKey.h to set other flag values.
    
    // Set the public key dictionary.
    [publicKeyAttr setObject:[NSNumber numberWithBool:YES] forKey:(id)kSecAttrIsPermanent];
    [publicKeyAttr setObject:publicTag forKey:(id)kSecAttrApplicationTag];
    // See SecKey.h to set other flag values.
    
    // Set attributes to top level dictionary.
    [keyPairAttr setObject:privateKeyAttr forKey:(id)kSecPrivateKeyAttrs];
    [keyPairAttr setObject:publicKeyAttr forKey:(id)kSecPublicKeyAttrs];
    
    // SecKeyGeneratePair returns the SecKeyRefs just for educational purposes.
    sanityCheck = SecKeyGeneratePair((CFDictionaryRef)keyPairAttr, &publicKeyRef, &privateKeyRef);
    LOGGING_FACILITY( sanityCheck == noErr && publicKeyRef != NULL && privateKeyRef != NULL, @"Something really bad went wrong with generating the key pair." );
    
    [privateKeyAttr release];
    [publicKeyAttr release];
    [keyPairAttr release];
    PublicKey *publicKey = [[KeyMaster getPublicKey] retain];
    PrivateKey *privateKey = [[PrivateKey alloc]init:KeyMaster.privateKeyRef];
    KeyPair *kp = [[KeyPair alloc]init:publicKey with:privateKey];
    [kp autorelease];
    [publicKey autorelease];
    [privateKey autorelease];
    return kp;
}

+ (PublicKey *) getPublicKey {
	OSStatus sanityCheck = noErr;
	NSData * publicKeyBits = nil;
    NSData* publicTag = KeyMaster.publicKeyTag;

	NSMutableDictionary * queryPublicKey = [[NSMutableDictionary alloc] init];
    
	// Set the public key query dictionary.
	[queryPublicKey setObject:(id)kSecClassKey forKey:(id)kSecClass];
	[queryPublicKey setObject:publicTag forKey:(id)kSecAttrApplicationTag];
	[queryPublicKey setObject:(id)kSecAttrKeyTypeRSA forKey:(id)kSecAttrKeyType];
	[queryPublicKey setObject:[NSNumber numberWithBool:YES] forKey:(id)kSecReturnData];
    
	// Get the key bits.
	sanityCheck = SecItemCopyMatching((CFDictionaryRef)queryPublicKey, (CFTypeRef *)&publicKeyBits);
    
	if (sanityCheck != noErr) {
        [queryPublicKey release];
        return nil;
	}
    
	[queryPublicKey release];
	PublicKey *pk = [[PublicKey alloc]init:publicKeyBits andRef:KeyMaster.publicKeyRef];
    [pk autorelease];
	return pk;
}

+ (PrivateKey *) getPrivateKey {
	PrivateKey *pk = [[PrivateKey alloc]init:KeyMaster.privateKeyRef];
    [pk autorelease];
	return pk;
}

+ (void) generateNewSecretKey {
	OSStatus sanityCheck = noErr;
    uint8_t *secretKey = NULL;
    NSData *secretTag = KeyMaster.secretKeyTag;
	NSData *secretKeyRef;
    
	// First delete current secret key.
	[self deleteSecretKey];

    // Container dictionary
	NSMutableDictionary *secretKeyAttr = [[NSMutableDictionary alloc] init];
	[secretKeyAttr setObject:(id)kSecClassKey forKey:(id)kSecClass];
	[secretKeyAttr setObject:secretTag forKey:(id)kSecAttrApplicationTag];
	[secretKeyAttr setObject:[NSNumber numberWithUnsignedInt:CSSM_ALGID_3DES_3KEY] forKey:(id)kSecAttrKeyType];
	[secretKeyAttr setObject:[NSNumber numberWithUnsignedInt:(unsigned int)(kChosenCipherKeySize << 3)] forKey:(id)kSecAttrKeySizeInBits];
	[secretKeyAttr setObject:[NSNumber numberWithUnsignedInt:(unsigned int)(kChosenCipherKeySize << 3)]	forKey:(id)kSecAttrEffectiveKeySize];
	[secretKeyAttr setObject:(id)kCFBooleanTrue forKey:(id)kSecAttrCanEncrypt];
	[secretKeyAttr setObject:(id)kCFBooleanTrue forKey:(id)kSecAttrCanDecrypt];
	[secretKeyAttr setObject:(id)kCFBooleanFalse forKey:(id)kSecAttrCanDerive];
	[secretKeyAttr setObject:(id)kCFBooleanFalse forKey:(id)kSecAttrCanSign];
	[secretKeyAttr setObject:(id)kCFBooleanFalse forKey:(id)kSecAttrCanVerify];
	[secretKeyAttr setObject:(id)kCFBooleanFalse forKey:(id)kSecAttrCanWrap];
	[secretKeyAttr setObject:(id)kCFBooleanFalse forKey:(id)kSecAttrCanUnwrap];
	
	// Allocate some buffer space. I don't trust calloc.
	secretKey = malloc( kChosenCipherKeySize * sizeof(uint8_t) );
	
	LOGGING_FACILITY( secretKey != NULL, @"Problem allocating buffer space for secret key generation." );
	
	memset((void *)secretKey, 0x0, kChosenCipherKeySize);
	
	sanityCheck = SecRandomCopyBytes(kSecRandomDefault, kChosenCipherKeySize, secretKey);
	LOGGING_FACILITY1( sanityCheck == noErr, @"Problem generating the secret key, OSStatus == %d.", (int)sanityCheck );
	
	secretKeyRef = [[NSData alloc] initWithBytes:(const void *)secretKey length:kChosenCipherKeySize];
	
	// Add the wrapped key data to the container dictionary.
	[secretKeyAttr setObject:secretKeyRef forKey:(id)kSecValueData];
	
	// Add the secret key to the keychain.
	sanityCheck = SecItemAdd((CFDictionaryRef) secretKeyAttr, NULL);
	LOGGING_FACILITY1( sanityCheck == noErr || sanityCheck == errSecDuplicateItem, @"Problem storing the secret key in the keychain, OSStatus == %d.", (int)sanityCheck );
	
	if (secretKey) free(secretKey);
	[secretKeyAttr release];
    [secretKeyRef release];
}

+ (NSData *) getSecretKeyBytes {
    OSStatus sanityCheck = noErr;
    NSData *secretTag = KeyMaster.secretKeyTag;
    NSData *secretKeyBytes = nil;
    
    NSMutableDictionary * querysecretKey = [[NSMutableDictionary alloc] init];
    
    // Set the private key query dictionary.
    [querysecretKey setObject:(id)kSecClassKey forKey:(id)kSecClass];
    [querysecretKey setObject:secretTag forKey:(id)kSecAttrApplicationTag];
    [querysecretKey setObject:[NSNumber numberWithUnsignedInt:CSSM_ALGID_3DES_3KEY] forKey:(id)kSecAttrKeyType];
    [querysecretKey setObject:[NSNumber numberWithBool:YES] forKey:(id)kSecReturnData];
    
    // Get the key bits.
    sanityCheck = SecItemCopyMatching((CFDictionaryRef)querysecretKey, (CFTypeRef *)&secretKeyBytes);
	LOGGING_FACILITY1( sanityCheck == noErr || sanityCheck == errSecItemNotFound, @"Error getting secret key, OSStatus == %d.", (int)sanityCheck );
    
    [querysecretKey release];
	return secretKeyBytes;
}

+ (void) deleteSecretKey {
	OSStatus sanityCheck = noErr;
    NSData *secretTag = KeyMaster.secretKeyTag;

	NSMutableDictionary * querysecretKey = [[NSMutableDictionary alloc] init];
	
	// Set the secret key query dictionary.
	[querysecretKey setObject:(id)kSecClassKey forKey:(id)kSecClass];
	[querysecretKey setObject:secretTag forKey:(id)kSecAttrApplicationTag];
	[querysecretKey setObject:[NSNumber numberWithUnsignedInt:CSSM_ALGID_3DES_3KEY] forKey:(id)kSecAttrKeyType];
	
	// Delete the secret key.
	sanityCheck = SecItemDelete((CFDictionaryRef)querysecretKey);
	LOGGING_FACILITY1( sanityCheck == noErr || sanityCheck == errSecItemNotFound, @"Error removing secret key, OSStatus == %d.", (int)sanityCheck );
	
	[querysecretKey release];
}

+ (SecretKey*) generateSecretKey {
    [[self class] generateNewSecretKey];
    NSData *keyData = [[self class] getSecretKeyBytes];
    SecretKey *key = [[[SecretKey alloc] init:keyData] autorelease];
    return key;
}

+ (SecretKey*) decodeSecretKey:(NSString*) data {
    NSData *keyData = [NSData dataWithBase64EncodedString:data];
    SecretKey *key = [[[SecretKey alloc] init:keyData] autorelease];
    return key;
}

+ (NSString*) encodeSecretKey:(SecretKey*) key {
    NSString *encoded = [key.keyData base64EncodedString];
    return encoded;
}

@end
