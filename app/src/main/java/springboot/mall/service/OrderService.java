package springboot.mall.service;

import springboot.mall.dto.CreateOrderRequest;
import springboot.mall.model.Ex_Order;

public interface OrderService {
    
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Ex_Order getOrderById(Integer orderId);
}
