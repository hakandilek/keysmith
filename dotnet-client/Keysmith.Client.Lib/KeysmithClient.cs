// --------------------------------------------------------------------------------------------------------------------
// <copyright file="KeysmithClient.cs" company="Hakan Dilek">
//   (c) 2013 Hakan Dilek
// </copyright>
// <summary>
//   Defines the KeysmithClient type.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

namespace Keysmith.Client.Lib
{
    using System;

    using log4net;

    /// <summary>
    /// The keysmith client.
    /// </summary>
    public class KeysmithClient
    {
        /// <summary>
        /// The base url.
        /// </summary>
        private const string BaseUrl = "http://localhost:8080";

        /// <summary>
        /// The url post key.
        /// </summary>
        private const string UrlPostKey = "{0}/keysmith/publicKey";

        /// <summary>
        /// The url update key.
        /// </summary>
        private const string UrlUpdateKey = "{0}/keysmith/publicKey/{1}";

        /// <summary>
        /// The url get key.
        /// </summary>
        private const string UrlGetKey = "{0}/keysmith/publicKey/{1}";

        /// <summary>
        /// The url remove key.
        /// </summary>
        private const string UrlRemoveKey = "{0}/keysmith/publicKey/{1}";

        /// <summary>
        /// The log.
        /// </summary>
        private static readonly ILog Log = LogManager.GetLogger(typeof(KeysmithClient));

        /// <summary>
        /// The base url.
        /// </summary>
        private readonly string baseUrl;

        /// <summary>
        /// The key master.
        /// </summary>
        private readonly KeyMaster keyMaster;

        /// <summary>
        /// Initializes a new instance of the <see cref="KeysmithClient"/> class.
        /// </summary>
        /// <param name="baseUrl">
        /// The base url.
        /// </param>
        public KeysmithClient(string baseUrl)
        {
            this.baseUrl = baseUrl;
            this.keyMaster = new KeyMaster();
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="KeysmithClient"/> class.
        /// </summary>
        public KeysmithClient()
            : this(BaseUrl)
        {
        }

        /// <summary>
        /// The update public key.
        /// </summary>
        /// <param name="keyId">
        /// The key id.
        /// </param>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        public string UpdatePublicKey(string keyId, PublicKey key)
        {
            string result = null;
            if (key != null)
            {
                string url = string.Format(UrlUpdateKey, this.baseUrl, keyId);
                Log.Info("updatePublicKey : " + url);
                string keyString = this.keyMaster.EncodePublicKey(key);
                Log.Info("key encoded : " + keyString);
                try
                {
                    result = HttpUtils.PostRaw(url, keyString);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                }                
            }

            return result;
        }

        /// <summary>
        /// The post public key.
        /// </summary>
        /// <param name="key">
        /// The key.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        public string PostPublicKey(PublicKey key)
        {
            string result = null;
            if (key != null)
            {
                string url = string.Format(UrlPostKey, BaseUrl);
                Log.Info("postPublicKey : " + url);
                string keyString = this.keyMaster.EncodePublicKey(key);
                Log.Info("key encoded : " + keyString);
                result = HttpUtils.PostRaw(url, keyString);
            }

            return result;
        }

        /// <summary>
        /// The get public key.
        /// </summary>
        /// <param name="keyId">
        /// The key id.
        /// </param>
        /// <returns>
        /// The <see cref="PublicKey"/>.
        /// </returns>
        public PublicKey GetPublicKey(string keyId)
        {
            string url = string.Format(UrlGetKey, this.baseUrl, keyId);
            Log.Info("getPublicKey : " + url);
            string keyString = HttpUtils.GetRaw(url);
            if (keyString == null || string.Empty.Equals(keyString))
            {
                return null;
            }

            PublicKey key = this.keyMaster.DecodePublicKey(keyString);
            return key;
        }

        /// <summary>
        /// The remove public key.
        /// </summary>
        /// <param name="keyId">
        /// The key id.
        /// </param>
        /// <returns>
        /// The <see cref="PublicKey"/>.
        /// </returns>
        public PublicKey RemovePublicKey(string keyId)
        {
            string url = string.Format(UrlRemoveKey, this.baseUrl, keyId);
            Log.Info("removePublicKey : " + url);
            string keyString = HttpUtils.DeleteRaw(url);
            if (keyString == null || string.Empty.Equals(keyString))
            {
                return null;
            }

            PublicKey key = this.keyMaster.DecodePublicKey(keyString);
            return key;
        }
    }
}