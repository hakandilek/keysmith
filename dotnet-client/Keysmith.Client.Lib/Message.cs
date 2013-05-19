// --------------------------------------------------------------------------------------------------------------------
// <copyright file="Message.cs" company="Hakan Dilek">
//   (c) 2013 Hakan Dilek
// </copyright>
// <summary>
//   The message.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

namespace Keysmith.Client.Lib
{
    /// <summary>
    /// The message.
    /// </summary>
    public class Message
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="Message"/> class.
        /// </summary>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <param name="data">
        /// The data.
        /// </param>
        public Message(string key, string data)
        {
            this.Key = key;
            this.Data = data;
        }

        /// <summary>
        /// Gets or sets the data.
        /// </summary>
        public string Data { get; set; }

        /// <summary>
        /// Gets or sets the key.
        /// </summary>
        public string Key { get; set; }
    }
}