package lk.edu.ijse.ThogaKadeHibernate.dao;

import lk.edu.ijse.ThogaKadeHibernate.entity.SuperEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by oshan on 30-Oct-17.
 */
public interface SuperDAO <T extends SuperEntity>{
    boolean add(T t) throws Exception;
    boolean delete(T t) throws Exception;
    boolean update(T t) throws Exception;
    T get(int id) throws Exception;
    void setSession(Session session);
    List<T> getAll();
}
