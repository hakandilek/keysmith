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

    using Org.BouncyCastle.Crypto;
    using Org.BouncyCastle.Crypto.Generators;
    using Org.BouncyCastle.Crypto.Parameters;
    using Org.BouncyCastle.Math;
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
            var data = key.GetPublicKeyInfo().PublicKeyData.GetBytes();
            var encoded = Convert.ToBase64String(data);
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
            var r = new RsaKeyPairGenerator();
            BigInteger exponentBigInt = new BigInteger("10001", 16);
            var param = new RsaKeyGenerationParameters(
                exponentBigInt, // new BigInteger("10001", 16)  publicExponent
                new SecureRandom(),  // SecureRandom.getInstance("SHA1PRNG"),//prng
                keySize, // strength
                80); // certainty
            r.Init(param);
            var kp = r.GenerateKeyPair();
            var keyPair = new KeyPair { PublicKey = new PublicKey(kp.Public), PrivateKey = new PrivateKey() };
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
        #endregion
    }
}