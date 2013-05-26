package keysmith.service.health;


import com.yammer.metrics.core.HealthCheck;

public class KeystoreMetricCheck extends HealthCheck {

	private MetricKeyStore keyStore;

	public KeystoreMetricCheck(MetricKeyStore keyStore) {
		super(KeystoreMetricCheck.class.getSimpleName());
		this.keyStore = keyStore;
	}

	@Override
	protected Result check() throws Exception {
		StringBuilder sb = new StringBuilder();
        sb.append("GET    count : ").append(keyStore.getGetRequests().getCount()).append("\n");
        sb.append("  GET     rate : ").append(keyStore.getGetRequests().getOneMinuteRate()).append("/min\n");
        sb.append("  PUT    count : ").append(keyStore.getPutRequests().getCount()).append("\n");
        sb.append("  PUT     rate : ").append(keyStore.getPutRequests().getOneMinuteRate()).append("/min\n");
        sb.append("  UPDATE count : ").append(keyStore.getUpdateRequests().getCount()).append("\n");
        sb.append("  UPDATE  rate : ").append(keyStore.getUpdateRequests().getOneMinuteRate()).append("/min");
		return Result.healthy(sb.toString());
	}

}
