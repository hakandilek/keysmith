package keysmith.service.health;

import static com.codahale.metrics.MetricRegistry.name;
import keysmith.service.core.Keystore;
import keysmith.service.core.SimpleKey;
import keysmith.service.db.SimpleKeyDAO;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

public class MetricKeyStore implements Keystore {

	private Keystore keystore;
	private final Meter getRequests;
	private final Meter putRequests;
	private final Meter updateRequests;

	public MetricKeyStore(Keystore keystore, MetricRegistry metrics) {
		this.keystore = keystore;
		getRequests = metrics.meter(name(SimpleKeyDAO.class, "getRequests"));
		putRequests = metrics.meter(name(SimpleKeyDAO.class, "putRequests"));
		updateRequests = metrics.meter(name(SimpleKeyDAO.class,
				"updateRequests"));
	}

	public String put(SimpleKey key) {
		putRequests.mark();
		return keystore.put(key);
	}

	public SimpleKey update(String keyId, SimpleKey key) {
		updateRequests.mark();
		return keystore.update(keyId, key);
	}

	public SimpleKey removeKey(String keyId) {
		return keystore.removeKey(keyId);
	}

	public SimpleKey get(String keyId) {
		getRequests.mark();
		return keystore.get(keyId);
	}

	public Long size() {
		return keystore.size();
	}

	public Meter getGetRequests() {
		return getRequests;
	}

	public Meter getPutRequests() {
		return putRequests;
	}

	public Meter getUpdateRequests() {
		return updateRequests;
	}

}
