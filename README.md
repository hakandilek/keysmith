keysmith
========

keysmith is an asymmetric cryptography showcase for java, written with 
[dropwizard](https://github.com/codahale/dropwizard).

It uses a client-server scheme where clients generate a key pair, and store the 
public key on the server. Another client encrypts a message retrieving the key 
from the server and puts this encrypted message on the server. First client
retrieves the encrypted message from the server and decrypts it using his own
private key.

![asymmetric implementation](https://raw.github.com/hakandilek/keysmith/master/asymmetric.jpg "asymmetric implementation")

