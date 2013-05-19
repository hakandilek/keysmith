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
    using System;

    using Keysmith.Client.Lib;

    using log4net;

    using Microsoft.VisualStudio.TestTools.UnitTesting;

    /// <summary>
    /// The cryptographer test.
    /// </summary>
    [TestClass]
    public class CryptographerTest
    {
        /// <summary>
        /// The log.
        /// </summary>
        private static readonly ILog Log = LogManager.GetLogger(typeof(CryptographerTest));

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
            Assert.IsNotNull(msg.Key);

            var test = c.HybridDecrypt(msg, kp.PrivateKey);
            Assert.IsNotNull(test);
            Assert.AreEqual("test", test);
        }

        /// <summary>
        /// The encode with ios public key.
        /// </summary>
        [TestMethod]
        public void EncryptWithIosPublicKey()
        {
            var km = new KeyMaster();
            var c = new Cryptographer(km);

            const string KeyString =
                "MIIBCgKCAQEAr3a1JDZOo6oo6HGEhmFmkwmV6UNPdB4ZTZnv5KHI2j9Cc90h9aZvRkzd28NSh0fPP"
                + "/RxRMzAb5r08QgqcHWK5reBQGcj3k+f1gTyUlDssIBlbbP2Z/7VJsHPXoU53MLUZ4K/BPEKYkZV"
                + "CsWmVB07sWV4ThTsX934pxT+ybNH8FDdjGfLFwU3fINXQHVf34iwYcSJPWbtPb6dSrXD8c0h/X/"
                + "3WCaMjLhyvuXi4jdBLGAAP/UXBobqwFDcrW1CO5RSyEIjKcR2A6fvN1Kx6zGLzaZjWdb5miBU73"
                + "b6C0vjVjlIarK/+EYBrVUDLl3yBPfJn29SOoyQeejY8pTQ9XzgAwIDAQAB";
            PublicKey publicKey = km.DecodePublicKey(KeyString);

            Message msg = c.HybridEncrypt("test", publicKey);
            Assert.IsNotNull(msg);
            Assert.IsNotNull(msg.Key);
            Assert.IsNotNull(msg.Data);
            Log.Info("Key  : " + msg.Key);
            Log.Info("Data : " + msg.Data);
        }

        /// <summary>
        /// The convert base 64 encoded string.
        /// </summary>
        [TestMethod]
        public void ConvertBase64EncodedString()
        {
            const string Str = 
                "lij/8cKCOnBmuEO1wmJZR2kraaJGGqxeGcIK5dDIrnNcbwdtOPEaog5fwKP9H0Chz5k5MO6LJ3N5t"
                + "E7q0IrSFOKJKSio0ale3A99hMxdfZQvLruC6eEPKjumXgYwD0hAb+Guvwi09JJsLzUh5R+Ayr3c"
                + "n69pxsCa5pd07AifLskO+SpWL1xd0v3oZgrNWmomHJdKLsD6k9FMB2LkaEKWq1ld0ZS4t6coBLG"
                + "sw9Nh194xPvA8MHOJBIVDg5axMistOWMWBU+wNqhCaTeZBCDmNR+/1vZL2BsvmDI5tQOUo6Igv8"
                + "Sd06fdZBW6eX32AcYIKT7N9gJa+ama5eiY3cUNcQ==";
            var bytes = Convert.FromBase64String(Str);

            Assert.IsNotNull(bytes);
        }
    }
}
