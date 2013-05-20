//
//  Cryptographer.m
//  Keysmith IOS Client
//
//  Created by Hakan D on 04/05/2013.
//  Copyright (c) 2013 Hakan Dilek. All rights reserved.

#import "Cryptographer.h"

@implementation Cryptographer

- (NSString*) hybridDecrypt: (Message*) crypted withKey: (PrivateKey*) key {
	NSString* secretKeyData = [self decrypt:crypted.key withPrivateKey: key];
	SecretKey* secretKey = [KeyMaster decodeSecretKey:secretKeyData];
	NSString* message = [self decrypt:crypted.data withSecretKey:secretKey];
	return message;
}

- (Message*) hybridEncrypt: (NSString*) message withKey: (PublicKey*) key {
	SecretKey* secretKey = [KeyMaster generateSecretKey];
	NSString* encryptedMsg = [self encrypt:message withSecretKey:secretKey];
	NSString* encryptedKey = [self encryptSecretKey:secretKey withPublicKey:key];
	Message* msg = [[Message alloc] init:encryptedKey data:encryptedMsg];
	[msg autorelease];
	return msg;
}

- (NSString*) publicDecrypt: (Message*) crypted withKey: (PrivateKey*) key {
	NSString* message = [self decrypt:crypted.data withPrivateKey:key];
	return message;
}

- (Message*) publicEncrypt: (NSString*) message withKey: (PublicKey*) key {
	NSString* encryptedMsg = [self encrypt:message withPublicKey:key];
	Message* msg = [[Message alloc] init:nil data:encryptedMsg];
	[msg autorelease];
	return msg;
}

- (NSString*) symmetricDecrypt: (Message*) crypted {
	SecretKey* key = [KeyMaster decodeSecretKey:crypted.key];
	NSString* message = [self decrypt:crypted.data withSecretKey:key];
	return message;
}

- (Message*) symmetricEncrypt: (NSString*) message withKey: (SecretKey*) key {
	NSString* encryptedMsg = [self encrypt:message withSecretKey:key];
	NSString* encryptedKey = [KeyMaster encodeSecretKey:key];
	Message* msg = [[Message alloc] init:encryptedKey data:encryptedMsg];
	[msg autorelease];
	return msg;
}

//------------- private methods ----------------


- (NSString*) toUTF8String: (NSData*) bytes {
    if(bytes == nil) return nil;
    return [[[NSString alloc] initWithData:bytes encoding:NSUTF8StringEncoding] autorelease];
}

- (NSData*) fromUTF8String: (NSString*) str {
    if(str == nil) return nil;
    return [str dataUsingEncoding:NSUTF8StringEncoding];
}

- (NSString*) decrypt: (NSString*) encrypted withPrivateKey: (PrivateKey*) pk {
	OSStatus sanityCheck = noErr;
	size_t cipherBufferSize = 0;
	size_t dataBufferSize = 0;
	uint8_t * keyBuffer = NULL;
	NSData * data = nil;
    NSData *encryptedData = [NSData dataWithBase64EncodedString:encrypted];
    SecKeyRef pkRef = pk.keyRef;
    
	LOGGING_FACILITY( pkRef != NULL, @"No private key found in the keychain." );
	
	// Calculate the buffer sizes.
	cipherBufferSize = SecKeyGetBlockSize(pkRef);
	dataBufferSize = [encryptedData length];
	LOGGING_FACILITY( dataBufferSize <= cipherBufferSize, @"Encrypted nonce is too large and falls outside multiplicative group." );
	
	// Allocate some buffer space. I don't trust calloc.
	keyBuffer = malloc( dataBufferSize * sizeof(uint8_t) );
	memset((void *)keyBuffer, 0x0, dataBufferSize);
	
	// Decrypt using the private key.
	sanityCheck = SecKeyDecrypt(pkRef,
                                kTypeOfWrapPadding,
                                (const uint8_t *) [encryptedData bytes],
                                cipherBufferSize,
                                keyBuffer,
                                &dataBufferSize
								);
	LOGGING_FACILITY1( sanityCheck == noErr, @"Error decrypting, OSStatus == %ld.", sanityCheck );
	
	// Build up plain text blob.
	data = [NSData dataWithBytes:(const void *)keyBuffer length:(NSUInteger)dataBufferSize];
	
	if (keyBuffer) free(keyBuffer);
    
    NSString *str = [self toUTF8String:data];
    return str;
}

- (NSString*) decrypt: (NSString*) encrypted withSecretKey: (SecretKey*) key {
    NSData *encBytes = [NSData dataWithBase64EncodedString:encrypted];
    NSData *skBytes = key.keyData;
	CCOptions pad = kSecretKeyPadding;
    NSData *data = [self doCipher:encBytes key:skBytes context:kCCDecrypt padding:&pad];
    NSString *str = [self toUTF8String:data];
    return str;
}

- (NSString*) encryptSecretKey: (SecretKey*) secretKey withPublicKey: (PublicKey*) key {
    NSData *dataBytes = [[secretKey.keyData base64EncodedString] dataUsingEncoding:NSUTF8StringEncoding];
    NSString *str = [self encryptData:dataBytes withPublicKey:key];
    return str;
}

- (NSString*) encrypt: (NSString*) plain withPublicKey: (PublicKey*) pk {
    NSData *dataBytes = [self fromUTF8String:plain];
    NSString *str = [self encryptData:dataBytes withPublicKey:pk];
    return str;
}

- (NSString*) encryptData: (NSData*) dataBytes withPublicKey: (PublicKey*) key {
	OSStatus sanityCheck = noErr;
	size_t cipherBufferSize = 0;
	size_t dataBufferSize = 0;
    SecKeyRef publicKey = key.keyRef;
	
	LOGGING_FACILITY( dataBytes != nil, @"Symmetric key parameter is nil." );
	LOGGING_FACILITY( publicKey != nil, @"Key parameter is nil." );
	
	NSData * cipher = nil;
	uint8_t * cipherBuffer = NULL;
	
	// Calculate the buffer sizes.
	cipherBufferSize = SecKeyGetBlockSize(publicKey);
	dataBufferSize = [dataBytes length];
	
	if (kTypeOfWrapPadding == kSecPaddingNone) {
		LOGGING_FACILITY( dataBufferSize <= cipherBufferSize, @"Nonce integer is too large and falls outside multiplicative group." );
	} else {
		LOGGING_FACILITY( dataBufferSize <= (cipherBufferSize - 11), @"Nonce integer is too large and falls outside multiplicative group." );
	}
	
	// Allocate some buffer space. I don't trust calloc.
	cipherBuffer = malloc( cipherBufferSize * sizeof(uint8_t) );
	memset((void *)cipherBuffer, 0x0, cipherBufferSize);
	
	// Encrypt using the public key.
	sanityCheck = SecKeyEncrypt(	publicKey,
                                kTypeOfWrapPadding,
                                (const uint8_t *)[dataBytes bytes],
                                dataBufferSize,
                                cipherBuffer,
                                &cipherBufferSize
								);
	
	LOGGING_FACILITY1( sanityCheck == noErr, @"Error encrypting, OSStatus == %ld.", sanityCheck );
	
	// Build up cipher text blob.
	cipher = [NSData dataWithBytes:(const void *)cipherBuffer length:(NSUInteger)cipherBufferSize];
	
	if (cipherBuffer) free(cipherBuffer);
	NSString *str = [cipher base64EncodedString];
	return str;
}


- (NSString*) encrypt: (NSString*) plain withSecretKey: (SecretKey*) key {
    NSData *data=  [self fromUTF8String: plain];
    NSData *skBytes = key.keyData;
	CCOptions pad = kSecretKeyPadding;
    NSData *enc = [self doCipher:data key:skBytes context:kCCEncrypt padding:&pad];
	NSString *str = [enc base64EncodedString];
    return str;
}

- (NSData *) doCipher:(NSData *)plainBytes key:(NSData *)keyBytes context:(CCOperation)encryptOrDecrypt padding:(CCOptions *)pkcs7 {
	CCCryptorStatus ccStatus = kCCSuccess;
	// Symmetric crypto reference.
	CCCryptorRef thisEncipher = NULL;
	// Cipher Text container.
	NSData * cipherOrPlainText = nil;
	// Pointer to output buffer.
	uint8_t * bufferPtr = NULL;
	// Total size of the buffer.
	size_t bufferPtrSize = 0;
	// Remaining bytes to be performed on.
	size_t remainingBytes = 0;
	// Number of bytes moved to buffer.
	size_t movedBytes = 0;
	// Length of plainBytes buffer.
	size_t plainBytesBufferSize = 0;
	// Placeholder for total written.
	size_t totalBytesWritten = 0;
	// A friendly helper pointer.
	uint8_t * ptr;
	
	// Initialization vector; dummy in this case 0's.
	uint8_t iv[kChosenCipherBlockSize];
	memset((void *) iv, 0x0, (size_t) sizeof(iv));
	
	LOGGING_FACILITY(plainBytes != nil, @"plainBytes object cannot be nil." );
	LOGGING_FACILITY(keyBytes != nil, @"Symmetric key object cannot be nil." );
	LOGGING_FACILITY(pkcs7 != NULL, @"CCOptions * pkcs7 cannot be NULL." );
	LOGGING_FACILITY([keyBytes length] == kChosenCipherKeySize, @"Disjoint choices for key size." );
    
	plainBytesBufferSize = [plainBytes length];
	
	LOGGING_FACILITY(plainBytesBufferSize > 0, @"Empty plainBytes passed in." );
	
	// We don't want to toss padding on if we don't need to
	if (encryptOrDecrypt == kCCEncrypt) {
		if (*pkcs7 != kCCOptionECBMode) {
			if ((plainBytesBufferSize % kChosenCipherBlockSize) == 0) {
				*pkcs7 = 0x0000;
			} else {
				*pkcs7 = kCCOptionPKCS7Padding;
			}
		}
	} else if (encryptOrDecrypt != kCCDecrypt) {
		LOGGING_FACILITY1( 0, @"Invalid CCOperation parameter [%d] for cipher context.", *pkcs7 );
	}
	
	// Create and Initialize the crypto reference.
	ccStatus = CCCryptorCreate(	encryptOrDecrypt,
                               kSecretKeyAlgorithm,
                               *pkcs7,
                               (const void *)[keyBytes bytes],
                               kChosenCipherKeySize,
                               (const void *)iv,
                               &thisEncipher
                               );
	
	LOGGING_FACILITY1( ccStatus == kCCSuccess, @"Problem creating the context, ccStatus == %d.", ccStatus );
	
	// Calculate byte block alignment for all calls through to and including final.
	bufferPtrSize = CCCryptorGetOutputLength(thisEncipher, plainBytesBufferSize, true);
	
	// Allocate buffer.
	bufferPtr = malloc( bufferPtrSize * sizeof(uint8_t) );
	
	// Zero out buffer.
	memset((void *)bufferPtr, 0x0, bufferPtrSize);
	
	// Initialize some necessary book keeping.
	
	ptr = bufferPtr;
	
	// Set up initial size.
	remainingBytes = bufferPtrSize;
	
	// Actually perform the encryption or decryption.
	ccStatus = CCCryptorUpdate( thisEncipher,
                               (const void *) [plainBytes bytes],
                               plainBytesBufferSize,
                               ptr,
                               remainingBytes,
                               &movedBytes
                               );
	
	LOGGING_FACILITY1( ccStatus == kCCSuccess, @"Problem with CCCryptorUpdate, ccStatus == %d.", ccStatus );
	
	// Handle book keeping.
	ptr += movedBytes;
	remainingBytes -= movedBytes;
	totalBytesWritten += movedBytes;
	
	// Finalize everything to the output buffer.
	ccStatus = CCCryptorFinal(	thisEncipher,
                              ptr,
                              remainingBytes,
                              &movedBytes
                              );
	
	totalBytesWritten += movedBytes;
	
	if (thisEncipher) {
		(void) CCCryptorRelease(thisEncipher);
		thisEncipher = NULL;
	}
	
	LOGGING_FACILITY1( ccStatus == kCCSuccess, @"Problem with encipherment ccStatus == %d", ccStatus );
	
	cipherOrPlainText = [NSData dataWithBytes:(const void *)bufferPtr length:(NSUInteger)totalBytesWritten];
    
	if (bufferPtr) free(bufferPtr);
	
	return cipherOrPlainText;
}

@end
