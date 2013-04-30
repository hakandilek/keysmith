package keysmith.server.db;

import keysmith.server.core.Keystore;
import keysmith.server.core.SimpleKey;

import org.hibernate.SessionFactory;

import com.yammer.dropwizard.hibernate.AbstractDAO;

public class SimpleKeyDAO extends AbstractDAO<SimpleKey> implements Keystore {

	public SimpleKeyDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public String put(SimpleKey key) {
		SimpleKey persisted = super.persist(key);
		return persisted.getId();
	}

	public String update(String keyId, SimpleKey key) {
		super.persist(key);
		return keyId;
	}

	public SimpleKey removeKey(String keyId) {
		SimpleKey obj = get(keyId);
		currentSession().delete(obj);
		return obj;
	}

	public SimpleKey get(String keyId) {
		return super.get(keyId);
	}

}
