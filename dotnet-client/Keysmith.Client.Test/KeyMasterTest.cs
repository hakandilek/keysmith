using System;
using System.Security.Cryptography.X509Certificates;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Keysmith.Client.Lib;

namespace Keysmith.Client.Test
{
    [TestClass]
    public class KeyMasterTest
    {
        [TestMethod]
        public void TestEncodeDecodePublicKey()
        {
            var keyMaster = new KeyMaster();
            var pk = new PublicKey();
            var encoded = keyMaster.encodePublicKey(pk);
            Assert.IsNotNull(encoded);
            Assert.AreNotSame("", encoded);

            var pk2 = keyMaster.decodePublicKey(encoded);
            Assert.IsNotNull(pk2);
            Assert.AreEqual(pk, pk2);
        }
    }
}
