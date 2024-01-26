package springboot.mall.service;

import springboot.mall.dto.CreateOrderRequest;

public interface OrderService {
    
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
