// --------------------------------------------------------------------------------------------------------------------
// <copyright file="HttpUtils.cs" company="Hakan Dilek">
//   (c) 2013 Hakan Dilek
// </copyright>
// <summary>
//   The http utils.
// </summary>
// --------------------------------------------------------------------------------------------------------------------

namespace Keysmith.Client.Lib
{
    using System;
    using System.IO;
    using System.Net;

    using log4net;

    /// <summary>
    /// The http utils.
    /// </summary>
    public class HttpUtils
    {
        /// <summary>
        /// The log.
        /// </summary>
        private static readonly ILog Log = LogManager.GetLogger(typeof(HttpUtils));

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
            string result = null;
            var request = WebRequest.Create(url) as HttpWebRequest;
            if (request != null)
            {
                request.ContentType = "application/json";
                request.Method = "POST";

                using (var streamWriter = new StreamWriter(request.GetRequestStream()))
                {
                    streamWriter.Write(postData);
                    streamWriter.Flush();
                    streamWriter.Close();

                    var response = (HttpWebResponse)request.GetResponse();
                    var rs = response.GetResponseStream();
                    if (rs != null)
                    {
                        using (var streamReader = new StreamReader(rs))
                        {
                            result = streamReader.ReadToEnd();
                        }
                    }
                }
            }

            return result;
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

                var webReq = WebRequest.Create(url) as HttpWebRequest;
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
                        if (resStream != null)
                        {
                            var reader = new StreamReader(resStream);
                            ret = reader.ReadToEnd();
                        }
                    }
                    catch (Exception e)
                    {
                        if (Log.IsErrorEnabled)
                        {
                            Log.Error("Exception on request", e);
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
                    if (Log.IsErrorEnabled)
                    {
                        Log.Error(msg);
                    }
                }
            }
            catch (Exception ex)
            {
                if (Log.IsErrorEnabled)
                {
                    Log.ErrorFormat("error occured:{0}", ex);
                }
            }

            return string.Empty;
        }

        /// <summary>
        /// The delete raw.
        /// </summary>
        /// <param name="url">
        /// The url.
        /// </param>
        /// <returns>
        /// The <see cref="string"/>.
        /// </returns>
        public static string DeleteRaw(string url)
        {
            try
            {
                string ret = null;

                var webReq = WebRequest.Create(url) as HttpWebRequest;
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
                        if (resStream != null)
                        {
                            var reader = new StreamReader(resStream);
                            ret = reader.ReadToEnd();
                        }
                    }
                    catch (Exception e)
                    {
                        if (Log.IsErrorEnabled)
                        {
                            Log.Error("Exception on request", e);
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
                    if (Log.IsErrorEnabled)
                    {
                        Log.Error(msg);
                    }
                }
            }
            catch (Exception ex)
            {
                if (Log.IsErrorEnabled)
                {
                    Log.ErrorFormat("error occured:{0}", ex);
                }
            }

            return string.Empty;
        }
    }
}
