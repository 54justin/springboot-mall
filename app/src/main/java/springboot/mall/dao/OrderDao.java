package springboot.mall.dao;

import java.util.List;

import springboot.mall.dto.OrderQueryParams;
import springboot.mall.model.Ex_Order;
import springboot.mall.model.Ex_Order_Item;

public interface OrderDao {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Ex_Order> getOrders(OrderQueryParams orderQueryParams);

    Ex_Order getOrderById(Integer orderId);

    List<Ex_Order_Item> getOrderItemsByOrderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<Ex_Order_Item> orderItemList);
}
