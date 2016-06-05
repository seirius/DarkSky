package darksky.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private SessionFactory factory;
	
	public HibernateUtil() {
		Configuration configuration = new Configuration().configure(HibernateUtil.class.getResource("/darksky/resources/hibernate.cfg.xml"));
		factory = configuration.buildSessionFactory();
	}
	
	public SessionFactory getSessionFactory() {
		return factory;
	}

	public void close() {
		factory.close();
	}
}
