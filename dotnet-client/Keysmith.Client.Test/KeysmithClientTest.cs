// --------------------------------------------------------------------------------------------------------------------
// <copyright file="KeysmithClientTest.cs" company="Hakan Dilek">
//   (c) 2013 Hakan Dilek
// </copyright>
// <summary>
//   The Keysmith client test.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

namespace Keysmith.Client.Test
{
    using Keysmith.Client.Lib;

    using Microsoft.VisualStudio.TestTools.UnitTesting;

    /// <summary>
    /// The Keysmith client test.
    /// </summary>
    [TestClass]
    public class KeysmithClientTest
    {
        /// <summary>
        /// The test get public key interface.
        /// </summary>
        [TestMethod]
        public void TestGetPublicKeyInterface()
        {
            var client = new KeysmithClient("http://localhost:8080");
            Assert.IsNull(client.GetPublicKey("test"));
            Assert.IsNull(client.GetPublicKey(string.Empty));
            Assert.IsNull(client.GetPublicKey(null));
        }

        /// <summary>
        /// The test remove public key interface.
        /// </summary>
        [TestMethod]
        public void TestRemovePublicKeyInterface()
        {
            var client = new KeysmithClient("http://localhost:8080");
            Assert.IsNull(client.RemovePublicKey("test"));
            Assert.IsNull(client.RemovePublicKey(string.Empty));
            Assert.IsNull(client.RemovePublicKey(null));
        }

        /// <summary>
        /// The test post public key interface.
        /// </summary>
        [TestMethod]
        public void TestPostPublicKeyInterface()
        {
            var client = new KeysmithClient("http://localhost:8080");
            var keyMaster = new KeyMaster();
            PublicKey pk = keyMaster.GenerateKeyPair().PublicKey;
            Assert.IsNotNull(client.PostPublicKey(pk));
            Assert.IsNull(client.PostPublicKey(null));
        }

        /// <summary>
        /// The test update public key interface.
        /// </summary>
        [TestMethod]
        public void TestUpdatePublicKeyInterface()
        {
            var client = new KeysmithClient("http://localhost:8080");
            var keyMaster = new KeyMaster();
            PublicKey pk = keyMaster.GenerateKeyPair().PublicKey;
            Assert.IsNull(client.UpdatePublicKey("test", pk));
            client.UpdatePublicKey(string.Empty, pk);
            client.UpdatePublicKey(null, pk);
            Assert.IsNull(client.UpdatePublicKey("test", null));
            Assert.IsNull(client.UpdatePublicKey(string.Empty, null));
            Assert.IsNull(client.UpdatePublicKey(null, null));
        }
    }
}
