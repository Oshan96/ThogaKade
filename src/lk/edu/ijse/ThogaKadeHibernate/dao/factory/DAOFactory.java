package lk.edu.ijse.ThogaKadeHibernate.dao.factory;

import lk.edu.ijse.ThogaKadeHibernate.dao.SuperDAO;
import lk.edu.ijse.ThogaKadeHibernate.dao.impl.CustomerDAOImpl;
import lk.edu.ijse.ThogaKadeHibernate.dao.impl.ItemDAOImpl;
import lk.edu.ijse.ThogaKadeHibernate.dao.impl.OrderDetailDAOImpl;
import lk.edu.ijse.ThogaKadeHibernate.dao.impl.OrdersDAOImpl;

/**
 * Created by oshan on 01-Nov-17.
 */
public class DAOFactory {
    private static DAOFactory factory;

    private DAOFactory(){

    }

    public static DAOFactory getInstance(){
        if(factory==null){
            factory=new DAOFactory();
        }
        return factory;
    }

    public SuperDAO getDAO(DAOType type){
        switch (type){
            case CUSTOMER: return new CustomerDAOImpl();
            case ITEM:return new ItemDAOImpl();
            case ORDERS:return new OrdersDAOImpl();
            case ORDERDETAIL:return new OrderDetailDAOImpl();
        }
        return null;
    }
}

