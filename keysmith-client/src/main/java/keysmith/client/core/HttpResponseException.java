package keysmith.client.core;

public class HttpResponseException extends Exception {

	private static final long serialVersionUID = 1L;
	private final int code;

	public HttpResponseException(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
