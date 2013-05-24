package keysmith.service.health;

import keysmith.service.core.Keystore;

import com.yammer.metrics.core.HealthCheck;

public class KeystoreSizeCheck extends HealthCheck {

	private Keystore keyStore;
	private int limit = 10000;

	public KeystoreSizeCheck(Keystore keyStore) {
		super(KeystoreSizeCheck.class.getSimpleName());
		this.keyStore = keyStore;
	}

	@Override
	protected Result check() throws Exception {
    	Long count = 0L;
		try {
			count = keyStore.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
        if (count > limit) {
            return Result.unhealthy("limit exceeded: " + count + " / " + limit);
        }
        return Result.healthy("count : " + count + " / " + limit);
	}

}
