keysmith
========

keysmith is a cross-platform cryptography showcase with following components:

 * public-key store - Java REST service implementation developed using [dropwizard](https://github.com/codahale/dropwizard).
 * [Java Client Library](https://github.com/hakandilek/keysmith/tree/master/keysmith-client)
 * [C#/.net Client Library](https://github.com/hakandilek/keysmith/tree/master/dotnet-client)
 * [Objective-C/IOS Client Library](https://github.com/hakandilek/keysmith/tree/master/ios-client)

Keysmith introduces 3 schemes for encrypting/decrypting data:

 * Public Key Encryption
 * Secret Key (Asymmetric) Encryption
 * Hybrid Encryption combining two above
 

Public Key Encryption
----------

With public key encryption clients generate a key pair, and store the 
public key on the server. Another client encrypts a message retrieving the key 
from the server and puts this encrypted message on the server. First client
retrieves the encrypted message from the server and decrypts it using his own
private key.

With this method, message size is limited to the key size 
(keysize / 8 - padding to be exact).

![asymmetric implementation](https://raw.github.com/hakandilek/keysmith/master/asymmetric.jpg "asymmetric implementation")

Secret Key Encryption
----------

With secret key encryption model, client generates a secret key and stores it 
within the message. Other client receiving the message decrypts it using the 
enclosed secret key. There's absolutely no security.

Hybrid Encryption
----------

Hybrid implementation is a combination of the above schemes. First client 
generates a key pair and a secret key. While key from the key pair is stored on 
the server, secret key is encrypted with this public key and message is encrypted
with the secret key. Thus, the message send to the server contains encrypted 
secret key and the encrypted message. Other client receiving the message first 
decrypts the secret key with the private key, then decrypts the message with 
the secret key.
 
This method has a slight advantage over the public encryption scheme because 
there's no limitation on the message size and performing secret key encryption on 
the message has a slightly huge performance advantage. 

![hybrid implementation](https://raw.github.com/hakandilek/keysmith/master/hybrid.jpg "hybrid implementation")
