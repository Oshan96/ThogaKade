package lk.edu.ijse.ThogaKadeHibernate.controller;

import lk.edu.ijse.ThogaKadeHibernate.dto.ItemDTO;
import lk.edu.ijse.ThogaKadeHibernate.entity.Item;
import lk.edu.ijse.ThogaKadeHibernate.service.ManagerService;
import lk.edu.ijse.ThogaKadeHibernate.service.factory.ServiceFactory;
import lk.edu.ijse.ThogaKadeHibernate.service.factory.ServiceType;

/**
 * Created by oshan on 30-Oct-17.
 */
public interface ManagerController extends SuperController {
    ManagerService service= (ManagerService) ServiceFactory.getInstance().getService(ServiceType.MANAGER);
    default boolean addNewItem(ItemDTO itemDTO){
        return service.addNewItem(new Item(
                itemDTO.getDescription(),
                itemDTO.getQtyOnHand(),
                itemDTO.getUnitPrice()
        ));
    }
}
