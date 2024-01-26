package springboot.mall.dao;

import java.util.List;

import springboot.mall.model.Ex_Order_Item;

public interface OrderDao {
    
    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<Ex_Order_Item> orderItemList);
}
