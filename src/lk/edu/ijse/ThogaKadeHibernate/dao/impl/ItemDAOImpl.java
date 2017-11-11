package lk.edu.ijse.ThogaKadeHibernate.dao.impl;

import lk.edu.ijse.ThogaKadeHibernate.dao.ItemDAO;
import lk.edu.ijse.ThogaKadeHibernate.entity.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by oshan on 01-Nov-17.
 */
public class ItemDAOImpl implements ItemDAO {
    private Session session;
    @Override
    public boolean add(Item item) throws Exception {
        return session.save(item)!=null;
    }

    @Override
    public boolean delete(Item item) throws Exception {
        try{
            session.delete(item);
            return true;
        }catch(HibernateException ex){
            throw ex;
        }
    }

    @Override
    public boolean update(Item item) throws Exception {
        try{
            session.update(item);
            return true;
        }catch(HibernateException ex){
            throw ex;
        }
    }

    @Override
    public Item get(int id) throws Exception {
        return (Item) session.createCriteria(Item.class).add(Restrictions.eq("itemCode",id)).list().get(0);
    }

    @Override
    public void setSession(Session session) {
        this.session=session;
    }

    @Override
    public List<Item> getAll() {

        return session.createCriteria(Item.class).list();
    }
}
