// --------------------------------------------------------------------------------------------------------------------
// <copyright file="KeyMaster.cs" company="Hakan Dilek">
//   (c) 2013 Hakan Dilek
// </copyright>
// <summary>
//   The key master.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

namespace Keysmith.Client.Lib
{
    using System;

    using Org.BouncyCastle.Asn1.X509;
    using Org.BouncyCastle.Crypto;
    using Org.BouncyCastle.Crypto.Generators;
    using Org.BouncyCastle.Crypto.Parameters;
    using Org.BouncyCastle.Security;

    /// <summary>
    ///     The key master.
    /// </summary>
    public class KeyMaster
    {
        #region Public Methods and Operators

        /// <summary>
        /// The decode public key.
        /// </summary>
        /// <param name="keyString">
        /// The key string.
        /// </param>
        /// <returns>
        /// The <see cref="PublicKey"/>.
        /// </returns>
        public PublicKey DecodePublicKey(string keyString)
        {
            byte[] data = Convert.FromBase64String(keyString);
            AsymmetricKeyParameter publicKeyParam = PublicKeyFactory.CreateKey(data);
            return new PublicKey(publicKeyParam);
        }

        /// <summary>
        /// The encode public key.
        /// </summary>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        public string EncodePublicKey(PublicKey key)
        {
            SubjectPublicKeyInfo publicKeyInfo = key.GetPublicKeyInfo();
            byte[] data = publicKeyInfo.ToAsn1Object().GetDerEncoded();
            string encoded = Convert.ToBase64String(data);             
            return encoded;
        }

        /// <summary>
        /// generates a key pair.
        /// </summary>
        /// <param name="keySize">
        /// The key Size in bits
        /// </param>
        /// <returns>
        /// The <see cref="KeyPair"/>.
        /// </returns>
        public KeyPair GenerateKeyPair(int keySize)
        {
            var g = new RsaKeyPairGenerator();
            g.Init(new KeyGenerationParameters(new SecureRandom(), keySize));
            var kp = g.GenerateKeyPair();
            var keyPair = new KeyPair { PublicKey = new PublicKey(kp.Public), PrivateKey = new PrivateKey(kp.Private) };
            return keyPair;
        }

        /// <summary>
        /// The generate 1024 bit key pair.
        /// </summary>
        /// <returns>
        /// The <see cref="KeyPair"/>.
        /// </returns>
        public KeyPair GenerateKeyPair()
        {
            return this.GenerateKeyPair(1024);
        }

        /// <summary>
        /// The encode secret key.
        /// </summary>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        public string EncodeSecretKey(SecretKey key)
        {
            var data = key.GetBytes();
            var encoded = Convert.ToBase64String(data);
            return encoded;
        }

        /// <summary>
        /// The decode secret key.
        /// </summary>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="SecretKey"/>.
        /// </returns>
        public SecretKey DecodeSecretKey(string key)
        {
            byte[] data = Convert.FromBase64String(key);
            return new SecretKey(data);
        }

        /// <summary>
        /// The generate secret key.
        /// </summary>
        /// <returns>
        /// The <see cref="SecretKey"/>.
        /// </returns>
        public SecretKey GenerateSecretKey()
        {
            KeyGenerationParameters kgp = new KeyGenerationParameters(
                new SecureRandom(), 
                DesEdeParameters.DesEdeKeyLength * 8);

            var kg = new DesEdeKeyGenerator();
            kg.Init(kgp);

            /*
            * Third, and finally, generate the key
            */
            var key = new SecretKey(kg.GenerateKey());
            return key;
        }

        #endregion
    }
}