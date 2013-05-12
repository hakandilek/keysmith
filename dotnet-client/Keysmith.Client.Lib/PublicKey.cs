// --------------------------------------------------------------------------------------------------------------------
// <copyright file="PublicKey.cs" company="Hakan Dilek">
//   (c) 2003 
// </copyright>
// <summary>
//   The public key.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

namespace Keysmith.Client.Lib
{
    using Org.BouncyCastle.Asn1.X509;
    using Org.BouncyCastle.Crypto;
    using Org.BouncyCastle.X509;

    /// <summary>
    /// The public key.
    /// </summary>
    public class PublicKey
    {
        /// <summary>
        /// The public key parameter
        /// </summary>
        private readonly AsymmetricKeyParameter publicKeyParam;

        /// <summary>
        /// Initializes a new instance of the <see cref="PublicKey"/> class.
        /// </summary>
        /// <param name="publicKeyParam">
        /// The public key parameter
        /// </param>
        public PublicKey(AsymmetricKeyParameter publicKeyParam)
        {
            this.publicKeyParam = publicKeyParam;
        }

        /// <summary>
        /// The get public key info.
        /// </summary>
        /// <returns>
        /// The <see cref="SubjectPublicKeyInfo"/>.
        /// </returns>
        public SubjectPublicKeyInfo GetPublicKeyInfo()
        {
            return SubjectPublicKeyInfoFactory.CreateSubjectPublicKeyInfo(this.publicKeyParam);
        }

        /// <summary>
        /// The get hash code.
        /// </summary>
        /// <returns>
        /// The <see cref="int"/>.
        /// </returns>
        public override int GetHashCode()
        {
            return this.publicKeyParam != null ? this.publicKeyParam.GetHashCode() : 0;
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

            var other = obj as PublicKey;
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
        protected bool Equals(PublicKey other)
        {
            return this.publicKeyParam.Equals(other.publicKeyParam);
        }
    }
}
