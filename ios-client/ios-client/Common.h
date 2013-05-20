//
//  Common.h
//  Keysmith
//
//  Created by Hakan D on 18/05/2013.
//  Copyright (c) 2013 Mobil Rapor. All rights reserved.
//

#ifndef Keysmith_Common_h
#define Keysmith_Common_h

//logging active only in debug mode
#ifdef DEBUG
#define log( s, ... ) NSLog( @"%@(%d): %@", [[NSString stringWithUTF8String:__FILE__] lastPathComponent], __LINE__, [NSString stringWithFormat:(s), ##__VA_ARGS__] )
#else
#define log( s, ... )
#endif


#endif
