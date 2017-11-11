package lk.edu.ijse.ThogaKadeHibernate.service;

import lk.edu.ijse.ThogaKadeHibernate.core.util.SessionFactoryUtil;
import lk.edu.ijse.ThogaKadeHibernate.dao.CustomerDAO;
import lk.edu.ijse.ThogaKadeHibernate.dao.ItemDAO;
import lk.edu.ijse.ThogaKadeHibernate.dao.OrderDetailDAO;
import lk.edu.ijse.ThogaKadeHibernate.dao.OrdersDAO;
import lk.edu.ijse.ThogaKadeHibernate.dao.factory.DAOFactory;
import lk.edu.ijse.ThogaKadeHibernate.dao.factory.DAOType;
import lk.edu.ijse.ThogaKadeHibernate.entity.Customer;
import lk.edu.ijse.ThogaKadeHibernate.entity.Item;
import lk.edu.ijse.ThogaKadeHibernate.entity.OrderDetail;
import lk.edu.ijse.ThogaKadeHibernate.entity.Orders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by oshan on 30-Oct-17.
 */
public interface SuperService {

    ItemDAO itemDAO= (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.ITEM);
    OrdersDAO ordersDAO= (OrdersDAO) DAOFactory.getInstance().getDAO(DAOType.ORDERS);
    CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    OrderDetailDAO odDAO=(OrderDetailDAO)DAOFactory.getInstance().getDAO(DAOType.ORDERDETAIL);
    SessionFactory sessionFactory= SessionFactoryUtil.getSession();

    default boolean placeNewOrder(Orders orders, List<OrderDetail> orderDetailList){


        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        try {
            transaction=session.beginTransaction();
            itemDAO.setSession(session);
            for(OrderDetail od: orderDetailList){
                itemDAO.update(od.getItem());
            }

            orders.setOrderDetailList(orderDetailList);

            ordersDAO.setSession(session);

            System.out.println("First");
            if(ordersDAO.add(orders)){

                odDAO.setSession(session);
                System.out.println("Done");
                //Set OrderIds
                for(OrderDetail od:orderDetailList){
                    od.setOrders(orders);
                    od.getOrderDetail_pk().setOid(orders.getOid());
                    System.out.println("Here oid "+orders.getOid());
                    if(!odDAO.add(od)){
                        transaction.rollback();
                        return false;
                    }
                    System.out.println("here 2");
                }
                transaction.commit();
            }else {
                transaction.rollback();
            }

            return true;
        }catch (HibernateException ex){
            ex.printStackTrace();
            if(transaction!=null){
                transaction.rollback();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction!=null){
                transaction.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    default List<Item> getAllItems(){
        SessionFactory sessionFactory= SessionFactoryUtil.getSession();
        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            itemDAO.setSession(session);
            List<Item> list= itemDAO.getAll();
            transaction.commit();
            return list;

        }catch(HibernateException ex){
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    default List<Customer> getAllCustomers(){

        Session session=sessionFactory.openSession();

        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            customerDAO.setSession(session);
            List<Customer> list=customerDAO.getAll();
            System.out.println("Length : "+list.size());
            transaction.commit();
            return list;
        }catch(HibernateException ex){
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    default boolean addCustomer(Customer customer){
        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            customerDAO.setSession(session);

            boolean bool= customerDAO.add(customer);
            transaction.commit();
            return bool;
        }catch (HibernateException ex){
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    default boolean modifyOrder(Orders orders){
        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            ordersDAO.setSession(session);
            for(int i=0;i<orders.getOrderDetailList().size();i++){
                OrderDetail od=orders.getOrderDetailList().get(i);
                if(od.getOrders()==null){
                    orders.removeOrderDetail(od);
                }
            }

            boolean val=ordersDAO.update(orders);
            transaction.commit();
            return val;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return false;
    }

}
