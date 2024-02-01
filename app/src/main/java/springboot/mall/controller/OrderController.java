package springboot.mall.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.mall.dto.CreateOrderRequest;
import springboot.mall.dto.OrderQueryParams;
import springboot.mall.model.Ex_Order;
import springboot.mall.service.OrderService;
import springboot.mall.util.Page;

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

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Ex_Order>> getOrders(
            @PathVariable Integer userId, 
            @RequestParam(defaultValue = "10") @Max(100) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ){
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        // 取得 order list
        List<Ex_Order> orderList = orderService.getOrders(orderQueryParams);

        // 取得 order 總數
        Integer count = orderService.countOrder(orderQueryParams);

        // 分頁
        Page<Ex_Order> page =  new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.CREATED).body(page);
    }    
}
