package lk.edu.ijse.ThogaKadeHibernate.dao.impl;

import lk.edu.ijse.ThogaKadeHibernate.dao.OrderDetailDAO;
import lk.edu.ijse.ThogaKadeHibernate.entity.OrderDetail;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by oshan on 01-Nov-17.
 */
public class OrderDetailDAOImpl implements OrderDetailDAO {
    private Session session;
    @Override
    public boolean add(OrderDetail orderDetail) throws Exception {
        return session.save(orderDetail)!=null;
    }

    @Override
    public boolean delete(OrderDetail orderDetail) throws Exception {
        try{
            session.delete(orderDetail);
            return true;
        }catch(HibernateException ex){
            throw ex;
        }
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws Exception {
        try{
            session.update(orderDetail);
            return true;
        }catch(HibernateException ex){
            throw ex;
        }
    }

    @Override
    public OrderDetail get(int id) throws Exception {
        return null;
    }

    @Override
    public void setSession(Session session) {
        this.session=session;
    }

    @Override
    public List<OrderDetail> getAll() {
        return session.createCriteria(OrderDetail.class).list();
    }
}
