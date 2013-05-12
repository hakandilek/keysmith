// --------------------------------------------------------------------------------------------------------------------
// <copyright file="KeyMasterTest.cs" company="Hakan Dilek">
//   (c) 2013
// </copyright>
// <summary>
//   The key master test.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

using System;

namespace Keysmith.Client.Test
{
    using Lib;

    using Microsoft.VisualStudio.TestTools.UnitTesting;

    /// <summary>
    /// The key master test.
    /// </summary>
    [TestClass]
    public class KeyMasterTest
    {
        /// <summary>
        /// The test decode public key.
        /// </summary>
        [TestMethod]
        public void TestDecodePublicKey()
        {
            var keyMaster = new KeyMaster();
            var keyString =
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCb1TXuuj1RESupASrVzk0U51oIPRaZVfdWPZo/VOljHTcfNl1/boHsp+IylC/tcVP9MYwiD5IQye78MjOQZ72rpJZMirmjE1P3mrckDFg1MexxsGn6/JRWWgh/AuN2T4/qkv5FXQvBxtLiaH9p4iV6lgCcTPEWZYMbpF4oiL4FwwIDAQAB";
            var publicKey = keyMaster.DecodePublicKey(keyString);
            Assert.IsNotNull(publicKey);
        }

        /// <summary>
        /// The test encode public key.
        /// </summary>
        [TestMethod]
        public void TestEncodePublicKey()
        {
            var keyMaster = new KeyMaster();
            PublicKey pk = keyMaster.GenerateKeyPair().PublicKey;
            var encoded = keyMaster.EncodePublicKey(pk);
            Assert.IsNotNull(encoded);
            Assert.AreNotSame(string.Empty, encoded);
        }

        [TestMethod]
        public void EncodeSecretKey()
        {
            throw new NotImplementedException();
        }

        [TestMethod]
        public void DecodeSecretKey()
        {
            throw new NotImplementedException();
        }

        [TestMethod]
        public void GenerateSecretKey()
        {
            throw new NotImplementedException();
        }

    }
}
