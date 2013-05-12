// --------------------------------------------------------------------------------------------------------------------
// <copyright file="SecretKey.cs" company="Hakan Dilek">
//   (c) Hakan Dilek
// </copyright>
// <summary>
//   Defines the SecretKey type.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

namespace Keysmith.Client.Lib
{
    using System.Linq;

    /// <summary>
    /// The secret key.
    /// </summary>
    public class SecretKey
    {
        /// <summary>
        /// The key bytes.
        /// </summary>
        private readonly byte[] bytes;

        /// <summary>
        /// Initializes a new instance of the <see cref="SecretKey"/> class.
        /// </summary>
        /// <param name="bytes">
        /// The key bytes.
        /// </param>
        public SecretKey(byte[] bytes)
        {
            this.bytes = bytes;
        }

        /// <summary>
        /// The get key bytes.
        /// </summary>
        /// <returns>
        /// The byte array holding the key.
        /// </returns>
        public byte[] GetBytes()
        {
            return this.bytes;
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

            var other = obj as SecretKey;
            return other != null && other.bytes.SequenceEqual(this.bytes);
        }

        /// <summary>
        /// The get hash code.
        /// </summary>
        /// <returns>
        /// The <see cref="int"/>.
        /// </returns>
        public override int GetHashCode()
        {
            return this.bytes != null ? this.bytes.GetHashCode() : 0;
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
        protected bool Equals(SecretKey other)
        {
            return this.bytes.SequenceEqual(other.bytes);
        }
    }
}
