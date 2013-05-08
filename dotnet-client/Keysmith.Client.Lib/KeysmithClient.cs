using System;
using System.Security.Cryptography.X509Certificates;
using log4net;

namespace Keysmith.Client.Lib
{
    public class KeysmithClient
    {
        private const string BaseUrl = "http://localhost:8080";
        private const string UrlPostKey = "{0}/keysmith/publicKey";
        private const string UrlUpdateKey = "{0}/keysmith/publicKey/{1}";
        private const string UrlGetKey = "{0}/keysmith/publicKey/{1}";
        private const string UrlRemoveKey = "{0}/keysmith/publicKey/{1}";

        private static readonly ILog Log = LogManager.GetLogger(typeof (KeysmithClient));

        private readonly string _baseUrl;
        private readonly KeyMaster _keyMaster;

        public KeysmithClient(String baseUrl)
        {
            _baseUrl = baseUrl;
            _keyMaster = new KeyMaster();
        }

        public KeysmithClient()
            : this(BaseUrl)
        {
        }

        public String UpdatePublicKey(String keyId, PublicKey key)
        {
            string url = String.Format(UrlUpdateKey, _baseUrl, keyId);
            Log.Info("updatePublicKey : " + url);
            String keyString = _keyMaster.encodePublicKey(key);
            Log.Info("key encoded : " + keyString);
            return HttpUtils.PostRaw(url, keyString);
        }

        public String PostPublicKey(PublicKey key)
        {
            String url = String.Format(UrlPostKey, BaseUrl);
            Log.Info("postPublicKey : " + url);
            String keyString = _keyMaster.encodePublicKey(key);
            Log.Info("key encoded : " + keyString);
            return HttpUtils.PostRaw(url, keyString);
        }

        public PublicKey GetPublicKey(String keyId)
        {
            String url = String.Format(UrlGetKey, _baseUrl, keyId);
            Log.Info("getPublicKey : " + url);
            String keyString = HttpUtils.GetRaw(url);
            PublicKey key = _keyMaster.decodePublicKey(keyString);
            return key;
        }

        public PublicKey RemovePublicKey(String keyId)
        {
            String url = String.Format(UrlRemoveKey, _baseUrl, keyId);
            Log.Info("removePublicKey : " + url);
            String keyString = HttpUtils.DeleteRaw(url);
            PublicKey key = _keyMaster.decodePublicKey(keyString);
            return key;
        }
    }
}