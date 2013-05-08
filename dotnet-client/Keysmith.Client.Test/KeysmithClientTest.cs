using System;
using System.Security.Cryptography.X509Certificates;
using Keysmith.Client.Lib;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Keysmith.Client.Test
{
    [TestClass]
    public class KeysmithClientTest
    {
        [TestMethod]
        public void TestGetPublicKeyInterface()
        {
            var client = new KeysmithClient("http://localhost:8080");
            Assert.IsNull(client.GetPublicKey("test"));
            Assert.IsNull(client.GetPublicKey(string.Empty));
            Assert.IsNull(client.GetPublicKey(null));
        }

        [TestMethod]
        public void TestRemovePublicKeyInterface()
        {
            var client = new KeysmithClient("http://localhost:8080");
            Assert.IsNull(client.RemovePublicKey("test"));
            Assert.IsNull(client.RemovePublicKey(string.Empty));
            Assert.IsNull(client.RemovePublicKey(null));
        }

        [TestMethod]
        public void TestPostPublicKeyInterface()
        {
            var client = new KeysmithClient("http://localhost:8080");
            var pk = new PublicKey();
            Assert.IsNull(client.PostPublicKey(pk));
            Assert.IsNull(client.PostPublicKey(null));
        }

        [TestMethod]
        public void TestUpdatePublicKeyInterface()
        {
            var client = new KeysmithClient("http://localhost:8080");
            var pk = new PublicKey();
            Assert.IsNull(client.UpdatePublicKey("test", pk));
            Assert.IsNull(client.RemovePublicKey(string.Empty, pk));
            Assert.IsNull(client.RemovePublicKey(null, pk));
            Assert.IsNull(client.UpdatePublicKey("test", null));
            Assert.IsNull(client.RemovePublicKey(string.Empty, null));
            Assert.IsNull(client.RemovePublicKey(null, null));
        }
    }
}
