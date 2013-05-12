// --------------------------------------------------------------------------------------------------------------------
// <copyright file="CryptographerTest.cs" company="Hakan Dilek">
//   (c) 2013 Hakan Dilek
// </copyright>
// <summary>
//   The cryptographer test.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

namespace Keysmith.Client.Test
{
    using Keysmith.Client.Lib;

    using Microsoft.VisualStudio.TestTools.UnitTesting;

    /// <summary>
    /// The cryptographer test.
    /// </summary>
    [TestClass]
    public class CryptographerTest
    {
        /// <summary>
        /// The test for public encrypt and decrypt.
        /// </summary>
        [TestMethod]
        public void PublicEncryptDecrypt()
        {
            var km = new KeyMaster();
            var c = new Cryptographer(km);

            KeyPair kp = km.GenerateKeyPair();

            var msg = c.PublicEncrypt("test", kp.PublicKey);
            Assert.IsNotNull(msg);
            Assert.IsNotNull(msg.Data);
            Assert.IsNull(msg.Key);

            var test = c.PublicDecrypt(msg, kp.PrivateKey);
            Assert.IsNotNull(test);
            Assert.AreEqual("test", test);
        }

        /// <summary>
        /// The test for symmetric encrypt and decrypt.
        /// </summary>
        [TestMethod]
        public void SymmetricEncryptDecrypt()
        {
            var km = new KeyMaster();
            var c = new Cryptographer(km);

            SecretKey key = km.GenerateSecretKey();

            var msg = c.SymmetricEncrypt("test", key);
            Assert.IsNotNull(msg);
            Assert.IsNotNull(msg.Data);
            Assert.IsNotNull(msg.Key);

            var test = c.SymmetricDecrypt(msg);
            Assert.IsNotNull(test);
            Assert.AreEqual("test", test);
        }

        /// <summary>
        /// The test for hybrid encrypt and decrypt.
        /// </summary>
        [TestMethod]
        public void HybridEncryptDecrypt()
        {
            var km = new KeyMaster();
            var c = new Cryptographer(km);

            KeyPair kp = km.GenerateKeyPair();

            var msg = c.HybridEncrypt("test", kp.PublicKey);
            Assert.IsNotNull(msg);
            Assert.IsNotNull(msg.Data);
            Assert.IsNull(msg.Key);

            var test = c.HybridDecrypt(msg, kp.PrivateKey);
            Assert.IsNotNull(test);
            Assert.AreEqual("test", test);
        }
    }
}