package lk.edu.ijse.ThogaKadeHibernate.core.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by oshan on 23-Oct-17.
 */
public class SessionFactoryUtil {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            ourSessionFactory =new Configuration().configure("/lk/edu/ijse/ThogaKadeHibernate/core/config/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSession() throws HibernateException {
        return ourSessionFactory;
    }


}