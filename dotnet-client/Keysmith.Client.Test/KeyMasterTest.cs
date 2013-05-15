// --------------------------------------------------------------------------------------------------------------------
// <copyright file="KeyMasterTest.cs" company="Hakan Dilek">
//   (c) 2013 Hakan Dilek
// </copyright>
// <summary>
//   The key master test.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

namespace Keysmith.Client.Test
{
    using Keysmith.Client.Lib;

    using Microsoft.VisualStudio.TestTools.UnitTesting;

    /// <summary>
    ///     The key master test.
    /// </summary>
    [TestClass]
    public class KeyMasterTest
    {
        #region Public Methods and Operators

        /// <summary>
        ///     The decode secret key.
        /// </summary>
        [TestMethod]
        public void DecodeSecretKey()
        {
            var keyMaster = new KeyMaster();
            const string KeyString =
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCb1TXuuj1RESupASrVzk0U51oIPRaZVfdWPZo/VOljHTcfNl1/boHsp+IylC/tcVP9MYwiD5IQye78MjOQZ72rpJZMirmjE1P3mrckDFg1MexxsGn6/JRWWgh/AuN2T4/qkv5FXQvBxtLiaH9p4iV6lgCcTPEWZYMbpF4oiL4FwwIDAQAB";
            SecretKey sk = keyMaster.DecodeSecretKey(KeyString);
            Assert.IsNotNull(sk);
            Assert.IsNotNull(sk.GetBytes());
        }

        /// <summary>
        ///     The encode secret key.
        /// </summary>
        [TestMethod]
        public void EncodeSecretKey()
        {
            var keyMaster = new KeyMaster();
            SecretKey sk = keyMaster.GenerateSecretKey();
            string encoded = keyMaster.EncodeSecretKey(sk);
            Assert.IsNotNull(encoded);
            Assert.AreNotSame(string.Empty, encoded);
        }

        /// <summary>
        /// The encode decode secret key.
        /// </summary>
        [TestMethod]
        public void EncodeDecodeSecretKey()
        {
            var keyMaster = new KeyMaster();
            SecretKey sk = keyMaster.GenerateSecretKey();
            string encoded = keyMaster.EncodeSecretKey(sk);
            Assert.IsNotNull(encoded);

            SecretKey sk2 = keyMaster.DecodeSecretKey(encoded);
            Assert.IsNotNull(sk2);
            Assert.AreEqual(sk, sk2);
        }

        /// <summary>
        ///     The generate secret key.
        /// </summary>
        [TestMethod]
        public void GenerateSecretKey()
        {
            var keyMaster = new KeyMaster();
            SecretKey sk = keyMaster.GenerateSecretKey();
            Assert.IsNotNull(sk);
        }

        /// <summary>
        ///     The test decode public key.
        /// </summary>
        [TestMethod]
        public void TestDecodePublicKey()
        {
            var keyMaster = new KeyMaster();
            const string KeyString =
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCb1TXuuj1RESupASrVzk0U51oIPRaZVfdWPZo/VOljHTcfNl1/boHsp+IylC/tcVP9MYwiD5IQye78MjOQZ72rpJZMirmjE1P3mrckDFg1MexxsGn6/JRWWgh/AuN2T4/qkv5FXQvBxtLiaH9p4iV6lgCcTPEWZYMbpF4oiL4FwwIDAQAB";
            PublicKey publicKey = keyMaster.DecodePublicKey(KeyString);
            Assert.IsNotNull(publicKey);
        }

        /// <summary>
        ///     The test encode public key.
        /// </summary>
        [TestMethod]
        public void TestEncodePublicKey()
        {
            var keyMaster = new KeyMaster();
            PublicKey pk = keyMaster.GenerateKeyPair().PublicKey;
            string encoded = keyMaster.EncodePublicKey(pk);
            Assert.IsNotNull(encoded);
            Assert.AreNotSame(string.Empty, encoded);
        }

        /// <summary>
        /// The test encode decode public key.
        /// </summary>
        [TestMethod]
        public void TestEncodeDecodePublicKey()
        {
            var keyMaster = new KeyMaster();
            PublicKey pk = keyMaster.GenerateKeyPair().PublicKey;
            string encoded = keyMaster.EncodePublicKey(pk);
            Assert.IsNotNull(encoded);

            var pk2 = keyMaster.DecodePublicKey(encoded);
            Assert.IsNotNull(pk2);
            Assert.AreEqual(pk, pk2);
        }

        #endregion
    }
}
