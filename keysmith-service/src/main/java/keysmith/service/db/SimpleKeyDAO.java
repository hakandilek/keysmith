package keysmith.service.db;

import keysmith.service.core.Keystore;
import keysmith.service.core.SimpleKey;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.yammer.dropwizard.hibernate.AbstractDAO;

public class SimpleKeyDAO extends AbstractDAO<SimpleKey> implements Keystore {

	private SessionFactory sessionFactory;
	private HashFunction hashFunction;

	public SimpleKeyDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		this.sessionFactory = sessionFactory;
		this.hashFunction = new HashFunction(2132213233);
		initialize(null);
	}

	public String put(SimpleKey key) {
		SimpleKey p = persist(key);
		Integer id = key.getId();
		String ukey = hashFunction.asString(id);
		p.setUkey(ukey);
		key.setUkey(ukey);
		persist(p);
		return ukey;
	}

	public SimpleKey update(String ukey, SimpleKey key) {
		SimpleKey k = get(ukey);
		if (k == null) {
			put(key);
			k = key;
		}
		k.setData(key.getData());
		SimpleKey persisted = super.persist(k);
		return persisted;
	}

	public SimpleKey removeKey(String ukey) {
		SimpleKey obj = get(ukey);
		currentSession().delete(obj);
		return obj;
	}

	public SimpleKey get(String ukey) {
		return uniqueResult(criteria().add(Restrictions.eq("ukey", ukey)));
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
