
namespace Keysmith.Client.Lib
{
    using System;
    using System.IO;
    using System.Net;

    using log4net;

    public class HttpUtils
    {
        /// <summary>
        /// The log.
        /// </summary>
        private readonly static ILog log = LogManager.GetLogger(typeof(HttpUtils));

        /// <summary>
        ///  raw POST request.
        /// </summary>
        /// <param name="url">
        /// The url.
        /// </param>
        /// <param name="postData">
        /// The post data.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        public static string PostRaw(string url, string postData)
        {
            var request = WebRequest.Create(url) as HttpWebRequest;
            request.ContentType = "application/json";
            request.Method = "POST";

            using (var streamWriter = new StreamWriter(request.GetRequestStream()))
            {
                streamWriter.Write(postData);
                streamWriter.Flush();
                streamWriter.Close();

                string result = null;
                var response = (HttpWebResponse)request.GetResponse();
                using (var streamReader = new StreamReader(response.GetResponseStream()))
                {
                    result = streamReader.ReadToEnd();
                }

                return result;
            }
        }

        /// <summary>
        /// raw GET request
        /// </summary>
        /// <param name="url">
        /// The url.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        public static string GetRaw(string url)
        {
            try
            {
                string ret = null;

                var webReq = System.Net.WebRequest.Create(url) as HttpWebRequest;
                if (webReq != null)
                {
                    webReq.Method = "GET";
                    webReq.ServicePoint.Expect100Continue = false;
                    webReq.Timeout = 20000;
                    webReq.ContentType = "application/json";

                    try
                    {
                        var result = (HttpWebResponse)webReq.GetResponse();
                        var resStream = result.GetResponseStream();
                        var reader = new StreamReader(resStream);
                        ret = reader.ReadToEnd();
                    }
                    catch (Exception e)
                    {
                        if (log.IsErrorEnabled)
                        {
                            log.Error("Exception on request", e);
                        }
                    }
                }

                return ret;
            }
            catch (WebException we)
            {
                if (we.Status == WebExceptionStatus.ProtocolError)
                {
                    var hresp = (HttpWebResponse)we.Response;
                    var msg = "Authentication Failed, " + hresp.StatusCode + "\r\n" +
                                 "Status Code: " + (int)hresp.StatusCode + "\r\n" +
                                 "Status Description: " + hresp.StatusDescription;
                    if (log.IsErrorEnabled)
                        log.Error(msg);
                }
            }
            catch (Exception ex)
            {
                if (log.IsErrorEnabled)
                    log.ErrorFormat("error occured:{0}", ex);
            }
            return "";
        }


        public static string DeleteRaw(string url)
        {
            try
            {
                string ret = null;

                var webReq = System.Net.WebRequest.Create(url) as HttpWebRequest;
                if (webReq != null)
                {
                    webReq.Method = "DELETE";
                    webReq.ServicePoint.Expect100Continue = false;
                    webReq.Timeout = 20000;
                    webReq.ContentType = "application/json";

                    try
                    {
                        var result = (HttpWebResponse)webReq.GetResponse();
                        var resStream = result.GetResponseStream();
                        var reader = new StreamReader(resStream);
                        ret = reader.ReadToEnd();
                    }
                    catch (Exception e)
                    {
                        if (log.IsErrorEnabled)
                        {
                            log.Error("Exception on request", e);
                        }
                    }
                }

                return ret;
            }
            catch (WebException we)
            {
                if (we.Status == WebExceptionStatus.ProtocolError)
                {
                    var hresp = (HttpWebResponse)we.Response;
                    var msg = "Authentication Failed, " + hresp.StatusCode + "\r\n" +
                                 "Status Code: " + (int)hresp.StatusCode + "\r\n" +
                                 "Status Description: " + hresp.StatusDescription;
                    if (log.IsErrorEnabled)
                        log.Error(msg);
                }
            }
            catch (Exception ex)
            {
                if (log.IsErrorEnabled)
                    log.ErrorFormat("error occured:{0}", ex);
            }
            return "";
        }
    }
}
