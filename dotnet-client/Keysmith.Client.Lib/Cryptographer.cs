// --------------------------------------------------------------------------------------------------------------------
// <copyright file="Cryptographer.cs" company="Hakan Dilek">
//   (c) 2013 Hakan Dilek
// </copyright>
// <summary>
//   The cryptographer.
// </summary>
// --------------------------------------------------------------------------------------------------------------------
namespace Keysmith.Client.Lib
{
    using System;
    using System.Text;

    using Org.BouncyCastle.Crypto.Encodings;
    using Org.BouncyCastle.Crypto.Engines;
    using Org.BouncyCastle.Crypto.Modes;
    using Org.BouncyCastle.Crypto.Paddings;
    using Org.BouncyCastle.Crypto.Parameters;

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
            string secretKeyData = this.Decrypt(crypted.Key, key);
            SecretKey secretKey = this.keyMaster.DecodeSecretKey(secretKeyData);
            string message = this.Decrypt(crypted.Data, secretKey);
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
        /// <param name="pk">
        /// The pk.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        private string Decrypt(string encrypted, PrivateKey pk)
        {
            var keyParam = pk.GetPrivateKeyParam();
            var engine = new Pkcs1Encoding(new RsaEngine());
            engine.Init(false, keyParam);
            var blockSize = engine.GetInputBlockSize();

            byte[] bytes = Convert.FromBase64String(encrypted);
            byte[] dec = engine.ProcessBlock(bytes, 0, blockSize);
            var clear = this.ToUTF8String(dec);
            return clear;
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
            byte[] bytes = Encoding.UTF8.GetBytes(data);
            var keyParam = pk.GetPublicKeyParam();
            var engine = new Pkcs1Encoding(new RsaEngine());
            engine.Init(true, keyParam);
            var blockSize = bytes.Length;// engine.GetInputBlockSize();
            byte[] enc = engine.ProcessBlock(bytes, 0, blockSize);
            return Convert.ToBase64String(enc);
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
        private string Encrypt(string data, SecretKey key)
        {
            byte[] bytes = Encoding.UTF8.GetBytes(data);

            // Setup the DESede cipher engine, create a PaddedBufferedBlockCipher in CBC mode.
            byte[] keyBytes = key.GetBytes();
            var cipher = new PaddedBufferedBlockCipher(new CbcBlockCipher(new DesEdeEngine()));

            // initialise the cipher with the key bytes, for encryption
            cipher.Init(true, new KeyParameter(keyBytes));

            int inBlockSize = bytes.Length;
            int outBlockSize = cipher.GetOutputSize(inBlockSize);

            var inblock = bytes;
            var outblock = new byte[outBlockSize];

            cipher.ProcessBytes(inblock, 0, inBlockSize, outblock, 0);
            cipher.DoFinal(outblock, 0);

            return Convert.ToBase64String(outblock);
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
        private string Decrypt(string encrypted, SecretKey key)
        {
            byte[] bytes = Convert.FromBase64String(encrypted);
            byte[] keyBytes = key.GetBytes();

            // initialise the cipher for decryption
            var cipher = new PaddedBufferedBlockCipher(new CbcBlockCipher(new DesEdeEngine()));
            cipher.Init(false, new KeyParameter(keyBytes));

            int inBlockSize = bytes.Length;
            int outBlockSize = cipher.GetOutputSize(inBlockSize);

            var inblock = bytes;
            var outblock = new byte[outBlockSize];

            cipher.ProcessBytes(inblock, 0, inBlockSize, outblock, 0);
            cipher.DoFinal(outblock, 0);

            var clear = this.ToUTF8String(outblock);
            return clear;
        }

        /// <summary>
        /// The to ut f 8 string.
        /// </summary>
        /// <param name="bytes">
        /// The bytes.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        private string ToUTF8String(byte[] bytes)
        {
            var len = Array.IndexOf(bytes, (byte)0);
            if (len < 0)
            {
                return Encoding.UTF8.GetString(bytes);
            }

            return Encoding.UTF8.GetString(bytes, 0, len);
        }

        #endregion
    }
}
