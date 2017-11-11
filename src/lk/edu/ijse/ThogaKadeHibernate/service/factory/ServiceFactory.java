package lk.edu.ijse.ThogaKadeHibernate.service.factory;

import lk.edu.ijse.ThogaKadeHibernate.service.SuperService;
import lk.edu.ijse.ThogaKadeHibernate.service.impl.CashierServiceImpl;
import lk.edu.ijse.ThogaKadeHibernate.service.impl.ManagerServiceImpl;

/**
 * Created by oshan on 01-Nov-17.
 */
public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private ServiceFactory(){

    }

    public static ServiceFactory getInstance(){
        if(serviceFactory==null){
            serviceFactory=new ServiceFactory();
        }
        return serviceFactory;
    }

    public SuperService getService(ServiceType type){
        switch (type){
            case CASHIER:return new CashierServiceImpl();
            case MANAGER:return new ManagerServiceImpl();
        }
        return null;
    }
}
