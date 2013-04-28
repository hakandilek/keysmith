package keysmith.common.core;

public class Message {

	private String key;
	private String data;

	public Message() {
		super();
	}

	public Message(String key, String data) {
		super();
		this.key = key;
		this.data = data;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Message [key=" + key + ", data=" + data + "]";
	}
	
	

}
