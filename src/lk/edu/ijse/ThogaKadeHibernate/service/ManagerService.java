package lk.edu.ijse.ThogaKadeHibernate.service;

import lk.edu.ijse.ThogaKadeHibernate.dao.ItemDAO;
import lk.edu.ijse.ThogaKadeHibernate.dao.factory.DAOFactory;
import lk.edu.ijse.ThogaKadeHibernate.dao.factory.DAOType;
import lk.edu.ijse.ThogaKadeHibernate.entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by oshan on 30-Oct-17.
 */
public interface ManagerService extends SuperService {

    default boolean addNewItem(Item item){
        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            itemDAO.setSession(session);
            boolean result = itemDAO.add(item);
            transaction.commit();
            return result;

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
