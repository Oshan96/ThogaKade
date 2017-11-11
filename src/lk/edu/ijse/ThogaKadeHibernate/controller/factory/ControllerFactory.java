package lk.edu.ijse.ThogaKadeHibernate.controller.factory;

import lk.edu.ijse.ThogaKadeHibernate.controller.SuperController;
import lk.edu.ijse.ThogaKadeHibernate.controller.impl.CashierControllerImpl;
import lk.edu.ijse.ThogaKadeHibernate.controller.impl.ManagerControllerImpl;

/**
 * Created by Owner on 11/4/2017.
 */
public class ControllerFactory {
    private static ControllerFactory controllerFactory;

    private ControllerFactory(){

    }

    public static ControllerFactory getInstance(){
        if(controllerFactory==null){
            controllerFactory=new ControllerFactory();
        }
        return controllerFactory;
    }

    public SuperController getController(ControllerType type){
        switch (type){
            case CASHIER:return new CashierControllerImpl();
            case MANAGER: return new ManagerControllerImpl();
        }
        return null;
    }
}
