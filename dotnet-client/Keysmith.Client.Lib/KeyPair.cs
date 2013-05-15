// --------------------------------------------------------------------------------------------------------------------
// <copyright file="KeyPair.cs" company="Hakan Dilek">
//   (c) 2013 Hakan Dilek
// </copyright>
// <summary>
//   The key pair.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

namespace Keysmith.Client.Lib
{
    /// <summary>
    /// The key pair.
    /// </summary>
    public class KeyPair
    {
        /// <summary>
        /// Gets or sets the public key.
        /// </summary>
        public PublicKey PublicKey { get; set; }

        /// <summary>
        /// Gets or sets the private key.
        /// </summary>
        public PrivateKey PrivateKey { get; set; }
    }
}