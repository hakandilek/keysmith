namespace Keysmith.Client.Lib
{
    public class Message
    {
        public Message(string key, string data)
        {
            this.Key = key;
            this.Data = data;
        }

        public string Data { get; set; }
        public string Key { get; set; }
    }
}