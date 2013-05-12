using System;

namespace Keysmith.Client.Lib
{
    public class Cryptographer
    {
        private readonly KeyMaster keyMaster;

        public Cryptographer(KeyMaster keyMaster)
        {
            this.keyMaster = keyMaster;
        }

        public Message PublicEncrypt(String message, PublicKey key)
        {
            return new Message(null, Encrypt(message, key));
        }

        public String PublicDecrypt(Message crypted, PrivateKey key)
        {
            return Decrypt(crypted.Data, key);
        }

        public Message SymmetricEncrypt(String message, SecretKey key)
        {
            String dataString = Encrypt(message, key);
            String keyString = keyMaster.EncodeSecretKey(key);
            return new Message(keyString, dataString);
        }

        public String SymmetricDecrypt(Message crypted)
        {
            SecretKey key = keyMaster.DecodeSecretKey(crypted.Key);
            return Decrypt(crypted.Data, key);
        }

        public Message HybridEncrypt(String message, PublicKey key)
        {
            SecretKey secretKey = keyMaster.GenerateSecretKey();
            String encryptedMsg = Encrypt(message, secretKey);
            String encryptedKey = Encrypt(secretKey, key);
            return new Message(encryptedKey, encryptedMsg);
        }

        public String HybridDecrypt(Message crypted, PrivateKey key)
        {
            String secretKeyData = Decrypt(crypted.Key, key);
            SecretKey secretKey = keyMaster.DecodeSecretKey(secretKeyData);
            String message = Decrypt(crypted.Data, secretKey);
            return message;
        }


        /**
	      * encrypts the given secret key with the given public key
	      */
        private String Encrypt(SecretKey secretKey, PublicKey key)
        {
            String data = keyMaster.EncodeSecretKey(secretKey);
            return Encrypt(data, key);
        }

        private String Encrypt(String data, PublicKey key)
        {
            //TODO
            throw new NotImplementedException();
        }

        private String Decrypt(String encrypted, PrivateKey key)
        {
            //TODO
            throw new NotImplementedException();
        }

        private String Encrypt(String data, SecretKey key)
        {
            //TODO
            throw new NotImplementedException();
        }

        private String Decrypt(String encrypted, SecretKey key)
        {
            //TODO
            throw new NotImplementedException();
        }
    }
}