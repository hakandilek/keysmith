//
//  KeysmithCommon.h
//  Mora
//
//  Created by Hakan D on 06/05/2013.
//  Copyright (c) 2013 Lion User. All rights reserved.
//

#ifndef Mora_KeysmithCommon_h
#define Mora_KeysmithCommon_h

#import <CommonCrypto/CommonDigest.h>
#import <CommonCrypto/CommonCryptor.h>

// The chosen symmetric key and digest algorithm chosen for this sample is AES and SHA1.
// The reasoning behind this was due to the fact that the iPhone and iPod touch have
// hardware accelerators for those particular algorithms and therefore are energy efficient.

#define kChosenCipherBlockSize	kCCBlockSize3DES
#define kChosenCipherKeySize	kCCKeySize3DES
#define kChosenDigestLength		CC_SHA1_DIGEST_LENGTH

// Global constants for padding schemes.
#define kTypeOfWrapPadding		kSecPaddingPKCS1
#define kSecretKeyPadding       kCCOptionPKCS7Padding

#define kSecretKeyAlgorithm     kCCAlgorithm3DES

// constants used to find public, private, and symmetric keys.
#define kPublicKeyTag			"keysmith.publickey"
#define kPrivateKeyTag			"keysmith.privatekey"
#define kSecretKeyTag           "keysmith.secretkey"


#if DEBUG
    #define LOGGING_FACILITY(X, Y)	\
                    NSAssert(X, Y);

    #define LOGGING_FACILITY1(X, Y, Z)	\
                    NSAssert1(X, Y, Z);
#else
    #define LOGGING_FACILITY(X, Y)	\
                if (!(X)) {			\
                      NSLog(Y);		\
                }

    #define LOGGING_FACILITY1(X, Y, Z)	\
                if (!(X)) {				\
                    NSLog(Y, Z);		\
                }
#endif


#endif
