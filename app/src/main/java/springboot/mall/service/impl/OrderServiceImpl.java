package springboot.mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import springboot.mall.dao.OrderDao;
import springboot.mall.dao.ProductDao;
import springboot.mall.dto.BuyItem;
import springboot.mall.dto.CreateOrderRequest;
import springboot.mall.model.Ex_Order;
import springboot.mall.model.Ex_Order_Item;
import springboot.mall.model.Product;
import springboot.mall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService{
    
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest){
        int totalAmount = 0;
        List<Ex_Order_Item> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            // 轉換 BuyItem to OrderItem
            Ex_Order_Item orderItem = new Ex_Order_Item();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            
            orderItemList.add(orderItem);
        }
        
        // 創建訂單 (Transactional)
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);
        
        return orderId;
    }

    @Override
    public Ex_Order getOrderById(Integer orderId){
        Ex_Order order = orderDao.getOrderById(orderId);
        
        List<Ex_Order_Item> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);
        
        return order;
    } 
}
