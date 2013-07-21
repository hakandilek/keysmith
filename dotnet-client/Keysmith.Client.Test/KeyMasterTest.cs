// --------------------------------------------------------------------------------------------------------------------
// <copyright file="KeyMasterTest.cs" company="Hakan Dilek">
//   (c) 2013 Hakan Dilek
// </copyright>
// <summary>
//   The key master test.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

using System.Linq;
using Keysmith.Client.Lib;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Keysmith.Client.Test
{
    /// <summary>
    ///     The key master test.
    /// </summary>
    [TestClass]
    public class KeyMasterTest
    {
        #region Public Methods and Operators

        /// <summary>
        ///     The test decode public key.
        /// </summary>
        [TestMethod]
        public void DecodePublicKey()
        {
            var km = new KeyMaster();
            const string keyString =
                "MIIBCgKCAQEAr3a1JDZOo6oo6HGEhmFmkwmV6UNPdB4ZTZnv5KHI2j9Cc90h9aZvRkzd28NSh0fPP"
                + "/RxRMzAb5r08QgqcHWK5reBQGcj3k+f1gTyUlDssIBlbbP2Z/7VJsHPXoU53MLUZ4K/BPEKYkZV"
                + "CsWmVB07sWV4ThTsX934pxT+ybNH8FDdjGfLFwU3fINXQHVf34iwYcSJPWbtPb6dSrXD8c0h/X/"
                + "3WCaMjLhyvuXi4jdBLGAAP/UXBobqwFDcrW1CO5RSyEIjKcR2A6fvN1Kx6zGLzaZjWdb5miBU73"
                + "b6C0vjVjlIarK/+EYBrVUDLl3yBPfJn29SOoyQeejY8pTQ9XzgAwIDAQAB";
            PublicKey publicKey = km.DecodePublicKey(keyString);
            Assert.IsNotNull(publicKey);

            string encoded = km.EncodePublicKey(publicKey);
            Assert.IsNotNull(encoded);
        }

        /// <summary>
        ///     The test decode public key.
        /// </summary>
        [TestMethod]
        public void DecodePublicKeys1024Bit()
        {
            var km = new KeyMaster();
            var keyStrings = new[]
                {
                    "MIGJAoGBALoIV6AH3gC3KbTE8mAXTricCf2qNZqR/6qbcjxwbKqEBk9Hak/pfpX7MbPOJNZP+gxpAvK1t74EiQ8RYFmXnzaruX9k4HFeRj6m2CmOVD5gzuY41zvKKsE1rr0CWASD7M9XiOrwIyw0CW4ZNSl7wRT6uLMVclpX+tVVKTjc2o7dAgMBAAE="
                    ,
                    "MIGJAoGBALnNs5r1GX25qYPWTh8R64MVF2Y/+DywL2urxTavkQpsGoehLlGV9DtBv0CFqmfhrVMkLvSWYq/TpCOik9nXRqtFCyplbWmIv8X8ecbK5T5lHzE1GlYU1ulrMWKOT8f0XHWoOTlXJG8cQxoVpBsCejnuYnRevQ/IRKYJmAN0RzNdAgMBAAE="
                    ,
                    "MIGJAoGBALx7/FFCf6GteaW+uv30xIdfrcUSGqs2KuRlLoG3+2QZR7pk1xDLnldoz/aj1fZrg15RCYe/ckp0Drevgmt7IOIpkHeoHGtv0yyfdNOWp0rlC3Ma1fWJ70p/Ch8lkqNtCHxts2fjpncDSTbE8VQ59dDktYTPpIhVKROm5lMGe9cHAgMBAAE="
                    ,
                    "MIGJAoGBANXUeKhgG/9I2LHbv0qBPihawfdB+uupRBMWXUjYKCutS4Ono2ikpZaoZUupda0useouv4ZXLStRzsZnVQAKjjTng0prAEVb+HAsvh8h8vbxNievzqnB4azVkPL+tcgZda7ay7ooYdidODgpfn4HqQsSu0j79fs7r1jcZvPxGiUDAgMBAAE="
                    ,
                };
            foreach (PublicKey publicKey in keyStrings.Select(km.DecodePublicKey))
            {
                Assert.IsNotNull(publicKey);

                string encoded = km.EncodePublicKey(publicKey);
                Assert.IsNotNull(encoded);
            }
        }


        /// <summary>
        ///     The test decode public key.
        /// </summary>
        [TestMethod]
        public void DecodePublicKeys2048Bit()
        {
            var km = new KeyMaster();
            var keyStrings = new[]
                {
                    "MIIBCgKCAQEAot5O95MHKNI5r7WUqt2wmgnZ4WqeOF4PKuL00cI+dfltJYXFxFdDHEZZ+g6Gs7tP1q19HfZhLP1E6MyE6y+RvU4xSR6Q4nIBWUo0KS7ehZwWCSmtIIC12r2OuofznMdbStRInpyTVikP2u9tna4mmJo8H2UVKRtXSqM63jMPx9fF/ndODfjxsaNvzG5BDgm/zZCEZaW9OEXxJNctTwKcAucNDOPocIYRp1RuJpL93knhB5byhRIg3deYiXVkXRvrsmynMIK9BHg1NEhWvGdimTThhWxyHjHo6O1FuiYoZ1zSc6AfOc88c6Ya/kXq+7rXVrNcwSoyyb8Ppr8n3zhEDQIDAQAB",
                    "MIIBCgKCAQEAqkEdekpxLEI0j7GTOhFuOQoyyT8zv83zLxVAb2GJ9VqwdnZ3CDpZ7m+ukfiXDf6jLVAJg/vzSWFTTA/fbrh4nLIrjIobBHGGzr/8I6ZhhnYm05MApkGCoe4Efe5OniR4UxvnjwgLD3lxBGeOTEPF4k1nCdbJg1jn4vnQ5K2XsyiwydynsYPWJtbTTMYMldLb+NH2WMrMBHq6K8FEmddRYkBGZfVpVSpGm4UiHIbeqW/2oyiaF11kF6+TmnlTeRxdEvJb1RG+/ed7gP7RJdTGdVATKj+t6kUCvfuhALGIOCYNw4TAc75PgMGCv6851sX7lMc3ACw5yeoKempIhBXrpQIDAQAB",
                    "MIIBCgKCAQEAr6Yr57hTk7vbNQqmTWhxfqAAuIe44ug9A/Z/Tn+8t3Gc+liu1GyTdse5LpuxfwGTHmHTWUDR/7em49UqKGjHuOePVF9lhQ2oeyayCV4h3YePAwWk4/mmXMPMftficN1AyIQ8T0Xug54Nz/gCnJb/nVmYc8Z78dFTyuERaowIoLVpkeU5uW22eRRPi1sl96mTYh6eN578uhV1pIBl8ksjyWw5osarCyv+XLriarCWTnNDYBpXwCEvmFCp4+DcoVEQigO1ckQSDoP6RX9iMYw9PsPH+kMCKtePiGcBNgMCkagiJzQ0eLZ0p7swk3NP6KvYijYkaG8kQPR5XRcVS3hd6wIDAQAB",
                };
            foreach (PublicKey publicKey in keyStrings.Select(km.DecodePublicKey))
            {
                Assert.IsNotNull(publicKey);

                string encoded = km.EncodePublicKey(publicKey);
                Assert.IsNotNull(encoded);
            }
        }
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
        ///     The test encode decode public key.
        /// </summary>
        [TestMethod]
        public void EncodeDecodePublicKey()
        {
            var keyMaster = new KeyMaster();
            PublicKey pk = keyMaster.GenerateKeyPair().PublicKey;
            string encoded = keyMaster.EncodePublicKey(pk);
            Assert.IsNotNull(encoded);

            PublicKey pk2 = keyMaster.DecodePublicKey(encoded);
            Assert.IsNotNull(pk2);
            Assert.AreEqual(pk, pk2);
        }

        /// <summary>
        ///     The encode decode secret key.
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
        ///     The test encode public key.
        /// </summary>
        [TestMethod]
        public void EncodePublicKey()
        {
            var keyMaster = new KeyMaster();
            PublicKey pk = keyMaster.GenerateKeyPair().PublicKey;
            string encoded = keyMaster.EncodePublicKey(pk);
            Assert.IsNotNull(encoded);
            Assert.AreNotSame(string.Empty, encoded);
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
        ///     The generate secret key.
        /// </summary>
        [TestMethod]
        public void GenerateSecretKey()
        {
            var keyMaster = new KeyMaster();
            SecretKey sk = keyMaster.GenerateSecretKey();
            Assert.IsNotNull(sk);
        }

        #endregion
    }
}