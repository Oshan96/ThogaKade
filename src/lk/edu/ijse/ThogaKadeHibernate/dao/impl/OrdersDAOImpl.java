package lk.edu.ijse.ThogaKadeHibernate.dao.impl;

import lk.edu.ijse.ThogaKadeHibernate.dao.OrdersDAO;
import lk.edu.ijse.ThogaKadeHibernate.entity.Orders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by oshan on 01-Nov-17.
 */
public class OrdersDAOImpl implements OrdersDAO {
    private Session session;
    @Override
    public boolean add(Orders orders) throws Exception {
        System.out.println("Came Here");
        return session.save(orders)!=null;
    }

    @Override
    public boolean delete(Orders orders) throws Exception {
        try{
            session.delete(orders);
            return true;
        }catch(HibernateException ex){
            throw ex;
        }
    }

    @Override
    public boolean update(Orders orders) throws Exception {
        try{
            session.saveOrUpdate(orders);
            return true;
        }catch (HibernateException ex){
            throw ex;
        }
    }

    @Override
    public Orders get(int id) throws Exception {
        return (Orders) session.createCriteria(Orders.class).add(Restrictions.eq("oid",id)).list().get(0);
    }

    @Override
    public void setSession(Session session) {
        this.session=session;
    }

    @Override
    public List<Orders> getAll() {
        return session.createCriteria(Orders.class).list();
    }
}
