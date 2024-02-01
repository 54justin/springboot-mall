package springboot.mall.service;

import java.util.List;

import springboot.mall.dto.CreateOrderRequest;
import springboot.mall.dto.OrderQueryParams;
import springboot.mall.model.Ex_Order;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Ex_Order> getOrders(OrderQueryParams orderQueryParams);
    
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Ex_Order getOrderById(Integer orderId);
}
