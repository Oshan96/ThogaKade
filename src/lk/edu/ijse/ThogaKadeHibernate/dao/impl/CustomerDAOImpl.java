package lk.edu.ijse.ThogaKadeHibernate.dao.impl;

import lk.edu.ijse.ThogaKadeHibernate.dao.CustomerDAO;
import lk.edu.ijse.ThogaKadeHibernate.entity.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by oshan on 01-Nov-17.
 */
public class CustomerDAOImpl implements CustomerDAO {
    private Session session;
    @Override
    public boolean add(Customer customer) throws Exception {
        return session.save(customer)!=null;
    }

    @Override
    public boolean delete(Customer customer) throws Exception {
        try{
            session.delete(customer);
            return true;
        }catch(HibernateException ex){
            throw ex;
        }
    }

    @Override
    public boolean update(Customer customer) throws Exception {
        try{
            session.update(customer);
            return true;
        }catch(HibernateException ex){
            throw ex;
        }
    }

    @Override
    public Customer get(int id) throws Exception {
        return (Customer) session.createCriteria(Customer.class).add(Restrictions.eq("cid",id)).list().get(0);
    }

    @Override
    public void setSession(Session session) {
        this.session=session;
    }

    @Override
    public List<Customer> getAll() {
        return session.createQuery("from Customer").list();
//        return session.createCriteria(Customer.class).list();
    }
}
