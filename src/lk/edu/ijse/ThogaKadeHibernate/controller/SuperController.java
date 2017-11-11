package lk.edu.ijse.ThogaKadeHibernate.controller;

import lk.edu.ijse.ThogaKadeHibernate.dto.CustomerDTO;
import lk.edu.ijse.ThogaKadeHibernate.dto.ItemDTO;
import lk.edu.ijse.ThogaKadeHibernate.dto.OrderDetailDTO;
import lk.edu.ijse.ThogaKadeHibernate.dto.OrdersDTO;
import lk.edu.ijse.ThogaKadeHibernate.entity.Customer;
import lk.edu.ijse.ThogaKadeHibernate.entity.Item;
import lk.edu.ijse.ThogaKadeHibernate.entity.OrderDetail;
import lk.edu.ijse.ThogaKadeHibernate.entity.Orders;
import lk.edu.ijse.ThogaKadeHibernate.service.CashierService;
import lk.edu.ijse.ThogaKadeHibernate.service.factory.ServiceFactory;
import lk.edu.ijse.ThogaKadeHibernate.service.factory.ServiceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oshan on 30-Oct-17.
 */
public interface SuperController {
    CashierService control= (CashierService) ServiceFactory.getInstance().getService(ServiceType.CASHIER);
    default boolean placeNewOrder(OrdersDTO ordersDTO, List<OrderDetailDTO> orderDetailDTOList){
        Customer customer=new Customer(
                ordersDTO.getCustomer().getCid(),
                ordersDTO.getCustomer().getName(),
                ordersDTO.getCustomer().getAddress(),
                ordersDTO.getCustomer().getContact(),
                ordersDTO.getCustomer().getNic()
        );
        Orders orders=new Orders(
                ordersDTO.getoDate(),
                ordersDTO.getTotal(),
                null,
                customer
        );
        List<OrderDetail> orderDetailList=new ArrayList<>();
        for(OrderDetailDTO dto:orderDetailDTOList){
            List<OrderDetail> odList=new ArrayList<>();
            odList.add(
                    new OrderDetail(
                            dto.getItem().getItemCode(),
                            dto.getOrders().getOid(),
                            dto.getQty(),
                            dto.getPrice()
                    )
            );
            Item item=new Item(
                    dto.getItem().getItemCode(),
                    dto.getItem().getDescription(),
                    dto.getItem().getQtyOnHand(),
                    dto.getItem().getUnitPrice(),
                    null
            );
            orderDetailList.add(
              new OrderDetail(
                      dto.getOrderDetail_pk(),
                      dto.getQty(),
                      dto.getPrice(),
                      item,
                      orders
              )
            );
        }
        return control.placeNewOrder(orders,orderDetailList);
    }

    default List<ItemDTO> getAllItems(){
        List<Item> list=control.getAllItems();
        List<ItemDTO> dtoList=new ArrayList<>();
        for(Item item:list){
            ItemDTO itemDTO=new ItemDTO(
                    item.getItemCode(),
                    item.getDescription(),
                    item.getQtyOnHand(),
                    item.getUnitPrice(),
                    null
            );
            List<OrderDetailDTO> odList=new ArrayList<>();
            for(OrderDetail od:item.getOrderDetailList()){
                odList.add(
                  new OrderDetailDTO(
                          od.getOrderDetail_pk(),
                          od.getQty(),
                          od.getPrice(),
                          itemDTO,
                          null
                  )
                );
            }
            itemDTO.setOrderDetailList(odList);
            dtoList.add(itemDTO);
        }
        return dtoList;
    }

    default List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOList=new ArrayList<>();
        List<Customer> custList=control.getAllCustomers();
        for (Customer customer:custList){
            CustomerDTO customerDTO=new CustomerDTO(
                    customer.getCid(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContact(),
                    customer.getNic(),
                    null
            );
            List<Orders> ordersList=customer.getOrdersList();
            List<OrdersDTO> ordersDTOList=new ArrayList<>();
            for (Orders odr:ordersList){
                OrdersDTO oDTO=new OrdersDTO(
                        odr.getOid(),
                        odr.getoDate(),
                        odr.getTotal(),
                        null,
                        customerDTO
                );
                List<OrderDetail> orderDetailList=odr.getOrderDetailList();
                List<OrderDetailDTO> orderDetailDTOList=new ArrayList<>();
                for(OrderDetail od:orderDetailList){
                    orderDetailDTOList.add(
                            new OrderDetailDTO(
                                    od.getOrderDetail_pk(),
                                    od.getQty(),
                                    od.getPrice(),
                                    new ItemDTO(
                                            od.getItem().getItemCode(),
                                            od.getItem().getDescription(),
                                            od.getItem().getQtyOnHand(),
                                            od.getItem().getUnitPrice(),
                                            null
                                    ),
                                    oDTO

                            )
                    );
                }
                oDTO.setOrderDetailList(orderDetailDTOList);
                ordersDTOList.add(oDTO);
            }
            customerDTO.setOrdersList(ordersDTOList);
            customerDTOList.add(customerDTO);
        }
        return customerDTOList;
    }

    default boolean addCustomer(CustomerDTO customerDTO){
        return control.addCustomer(new Customer(
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getContact(),
                customerDTO.getNic()
        ));
    }

    default boolean modifyOrder(OrdersDTO ordersDTO){
        List<OrderDetail> odList=new ArrayList<>();
        Orders orders=new Orders(
                ordersDTO.getOid(),
                ordersDTO.getoDate(),
                ordersDTO.getTotal(),
                odList,
                new Customer(
                        ordersDTO.getCustomer().getCid(),
                        ordersDTO.getCustomer().getName(),
                        ordersDTO.getCustomer().getAddress(),
                        ordersDTO.getCustomer().getContact(),
                        ordersDTO.getCustomer().getNic()
                )

        );

        for(OrderDetailDTO dto : ordersDTO.getOrderDetailList()){
            Item item=new Item(
                    dto.getItem().getItemCode(),
                    dto.getItem().getDescription(),
                    dto.getItem().getQtyOnHand(),
                    dto.getItem().getUnitPrice(),
                    null
            );
            OrderDetail od= new OrderDetail(
                    dto.getOrderDetail_pk(),
                    dto.getQty(),
                    dto.getPrice(),
                    item,
                    null
            );

            if(dto.getOrders()!=null){
                od.setOrders(orders);
            }
            odList.add(od);
        }
        return control.modifyOrder(orders);
    }
}
