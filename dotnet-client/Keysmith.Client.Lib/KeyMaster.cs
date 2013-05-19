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

    using Org.BouncyCastle.Asn1;
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
        /// <summary>
        /// The public key algorithm.
        /// </summary>
        private static readonly AlgorithmIdentifier PublicKeyAlgorithm = AlgorithmIdentifier.GetInstance("1.2.840.113549.1.1.1"); // OID for RSA

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
            var keyInfo = new SubjectPublicKeyInfo(PublicKeyAlgorithm, data);
            AsymmetricKeyParameter publicKeyParam = PublicKeyFactory.CreateKey(keyInfo);
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
            DerBitString bitString = publicKeyInfo.PublicKeyData;
            byte[] data = bitString.GetBytes();
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
            var puk = new PublicKey(kp.Public);
            var prk = new PrivateKey(kp.Private);
            var keyPair = new KeyPair { PublicKey = puk, PrivateKey = prk };
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
            var kgp = new KeyGenerationParameters(new SecureRandom(), DesEdeParameters.DesEdeKeyLength * 8);
            var kg = new DesEdeKeyGenerator();
            kg.Init(kgp);
            var key = new SecretKey(kg.GenerateKey());
            return key;
        }

        #endregion
    }
}