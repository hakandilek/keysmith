package keysmith.server.core;

public interface Keystore {

	SimpleKey get(String keyId);

	String put(SimpleKey key);

	SimpleKey removeKey(String keyId);

}