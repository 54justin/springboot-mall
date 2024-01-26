package springboot.mall.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import springboot.mall.dto.CreateOrderRequest;
import springboot.mall.model.Ex_Order;
import springboot.mall.service.OrderService;

@RestController
public class OrderController {
 
    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId, 
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){

        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Ex_Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // @PostMapping("/users/{userId}/orders")
    // public ResponseEntity<?> queryOrder(@PathVariable Integer userId, 
    //                                      @RequestBody @Valid CreateOrderRequest createOrderRequest){

    //     Integer orderId = orderService.createOrder(userId, createOrderRequest);

    //     return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    // }    
}