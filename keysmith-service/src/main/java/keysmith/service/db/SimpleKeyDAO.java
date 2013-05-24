package keysmith.service.db;

import keysmith.service.core.Keystore;
import keysmith.service.core.SimpleKey;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

import com.yammer.dropwizard.hibernate.AbstractDAO;

public class SimpleKeyDAO extends AbstractDAO<SimpleKey> implements Keystore {

	private SessionFactory sessionFactory;

	public SimpleKeyDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		this.sessionFactory = sessionFactory;
		initialize(null);
	}

	public String put(SimpleKey key) {
		SimpleKey persisted = super.persist(key);
		return persisted.getId();
	}

	public SimpleKey update(String keyId, SimpleKey key) {
		SimpleKey persisted = super.persist(key);
		return persisted;
	}

	public SimpleKey removeKey(String keyId) {
		SimpleKey obj = get(keyId);
		currentSession().delete(obj);
		return obj;
	}

	public SimpleKey get(String keyId) {
		return super.get(keyId);
	}

	@Override
	public Long size() {
		Long size = 0L;
		final Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(SimpleKey.class);
			size = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();
		} finally {
			session.close();
		}
		return size;
	}

}
