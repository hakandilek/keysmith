// --------------------------------------------------------------------------------------------------------------------
// <copyright file="PrivateKey.cs" company="Hakan Dilek">
//   (c) 2013 Hakan Dilek
// </copyright>
// <summary>
//   The private key.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

namespace Keysmith.Client.Lib
{
    using Org.BouncyCastle.Crypto;

    /// <summary>
    /// The private key.
    /// </summary>
    public class PrivateKey
    {
        /// <summary>
        /// The private key param.
        /// </summary>
        private readonly AsymmetricKeyParameter privateKeyParam;

        /// <summary>
        /// Initializes a new instance of the <see cref="PrivateKey"/> class.
        /// </summary>
        /// <param name="privateKeyParam">
        /// The private key param.
        /// </param>
        public PrivateKey(AsymmetricKeyParameter privateKeyParam)
        {
            // TODO: Complete member initialization
            this.privateKeyParam = privateKeyParam;
        }

        /// <summary>
        /// The get hash code.
        /// </summary>
        /// <returns>
        /// The <see cref="int"/>.
        /// </returns>
        public override int GetHashCode()
        {
            return this.privateKeyParam != null ? this.privateKeyParam.GetHashCode() : 0;
        }

        /// <summary>
        /// The equals.
        /// </summary>
        /// <param name="obj">
        /// The obj.
        /// </param>
        /// <returns>
        /// The <see cref="bool"/>.
        /// </returns>
        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            var other = obj as PrivateKey;
            return this.Equals(other);
        }

        /// <summary>
        /// The equals.
        /// </summary>
        /// <param name="other">
        /// The other.
        /// </param>
        /// <returns>
        /// The <see cref="bool"/>.
        /// </returns>
        protected bool Equals(PrivateKey other)
        {
            return this.privateKeyParam.Equals(other.privateKeyParam);
        }
    }
}