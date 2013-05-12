// --------------------------------------------------------------------------------------------------------------------
// <copyright file="Cryptographer.cs" company="">
//   
// </copyright>
// <summary>
//   The cryptographer.
// </summary>
// --------------------------------------------------------------------------------------------------------------------
namespace Keysmith.Client.Lib
{
    using System;
    using System.Collections.Generic;

    using Org.BouncyCastle.Crypto;
    using Org.BouncyCastle.Crypto.Engines;

    /// <summary>
    ///     The cryptographer.
    /// </summary>
    public class Cryptographer
    {
        #region Fields

        /// <summary>
        ///     The key master.
        /// </summary>
        private readonly KeyMaster keyMaster;

        #endregion

        #region Constructors and Destructors

        /// <summary>
        /// Initializes a new instance of the <see cref="Cryptographer"/> class.
        /// </summary>
        /// <param name="keyMaster">
        /// The key master.
        /// </param>
        public Cryptographer(KeyMaster keyMaster)
        {
            this.keyMaster = keyMaster;
        }

        #endregion

        #region Public Methods and Operators

        /// <summary>
        /// The hybrid decrypt.
        /// </summary>
        /// <param name="crypted">
        /// The crypted.
        /// </param>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        public string HybridDecrypt(Message crypted, PrivateKey key)
        {
            string secretKeyData = Decrypt(crypted.Key, key);
            SecretKey secretKey = this.keyMaster.DecodeSecretKey(secretKeyData);
            string message = Decrypt(crypted.Data, secretKey);
            return message;
        }

        /// <summary>
        /// The hybrid encrypt.
        /// </summary>
        /// <param name="message">
        /// The message.
        /// </param>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="Message"/>.
        /// </returns>
        public Message HybridEncrypt(string message, PublicKey key)
        {
            SecretKey secretKey = this.keyMaster.GenerateSecretKey();
            string encryptedMsg = this.Encrypt(message, secretKey);
            string encryptedKey = this.Encrypt(secretKey, key);
            return new Message(encryptedKey, encryptedMsg);
        }

        /// <summary>
        /// The public decrypt.
        /// </summary>
        /// <param name="crypted">
        /// The crypted.
        /// </param>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        public string PublicDecrypt(Message crypted, PrivateKey key)
        {
            return this.Decrypt(crypted.Data, key);
        }

        /// <summary>
        /// The public encrypt.
        /// </summary>
        /// <param name="message">
        /// The message.
        /// </param>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="Message"/>.
        /// </returns>
        public Message PublicEncrypt(string message, PublicKey key)
        {
            return new Message(null, this.Encrypt(message, key));
        }

        /// <summary>
        /// The symmetric decrypt.
        /// </summary>
        /// <param name="crypted">
        /// The crypted.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        public string SymmetricDecrypt(Message crypted)
        {
            SecretKey key = this.keyMaster.DecodeSecretKey(crypted.Key);
            return this.Decrypt(crypted.Data, key);
        }

        /// <summary>
        /// The symmetric encrypt.
        /// </summary>
        /// <param name="message">
        /// The message.
        /// </param>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="Message"/>.
        /// </returns>
        public Message SymmetricEncrypt(string message, SecretKey key)
        {
            string dataString = this.Encrypt(message, key);
            string keyString = this.keyMaster.EncodeSecretKey(key);
            return new Message(keyString, dataString);
        }

        #endregion

        #region Methods

        /// <summary>
        /// The decrypt.
        /// </summary>
        /// <param name="encrypted">
        /// The encrypted.
        /// </param>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        /// <exception cref="NotImplementedException">
        /// </exception>
        private string Decrypt(string encrypted, PrivateKey key)
        {
            // TODO
            throw new NotImplementedException();
        }

        /// <summary>
        /// The decrypt.
        /// </summary>
        /// <param name="encrypted">
        /// The encrypted.
        /// </param>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        /// <exception cref="NotImplementedException">
        /// </exception>
        private string Decrypt(string encrypted, SecretKey key)
        {
            // TODO
            throw new NotImplementedException();
        }

        /// <summary>
        /// encrypts the given secret key with the given public key
        /// </summary>
        /// <param name="secretKey">
        /// The secret key.
        /// </param>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        private string Encrypt(SecretKey secretKey, PublicKey key)
        {
            string data = this.keyMaster.EncodeSecretKey(secretKey);
            return this.Encrypt(data, key);
        }

        /// <summary>
        /// The encrypt.
        /// </summary>
        /// <param name="data">
        /// The data.
        /// </param>
        /// <param name="pk">
        /// The pk.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        private string Encrypt(string data, PublicKey pk)
        {
            pk.GetPublicKeyInfo();
            /*
            AsymmetricKeyParameter key = pk.GetPublicKeyInfo();
            var e = new RsaEngine();
            e.Init(true, key);

            int blockSize = e.GetInputBlockSize();

            var output = new List<byte>();

            for (int chunkPosition = 0; chunkPosition < data.Length; chunkPosition += blockSize)
            {
                int chunkSize = Math.Min(blockSize, data.Length - (chunkPosition * blockSize));
                output.AddRange(e.ProcessBlock(data, chunkPosition, chunkSize));
            }

            return output.ToArray();
            */
            throw new NotImplementedException();
        }

        /// <summary>
        /// The encrypt.
        /// </summary>
        /// <param name="data">
        /// The data.
        /// </param>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        /// <exception cref="NotImplementedException">
        /// </exception>
        private string Encrypt(string data, SecretKey key)
        {
            // TODO
            throw new NotImplementedException();
        }

        #endregion
    }
}