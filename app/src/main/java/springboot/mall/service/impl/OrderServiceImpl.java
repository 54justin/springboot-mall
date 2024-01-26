package springboot.mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import springboot.mall.dao.OrderDao;
import springboot.mall.dao.ProductDao;
import springboot.mall.dao.UserDao;
import springboot.mall.dto.BuyItem;
import springboot.mall.dto.CreateOrderRequest;
import springboot.mall.model.Ex_Order;
import springboot.mall.model.Ex_Order_Item;
import springboot.mall.model.Product;
import springboot.mall.model.User;
import springboot.mall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService{
    //添加log
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);    

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest){
        // 檢查 user 是否存在
        User user = userDao.getUserById(userId);

        if (user == null){
            log.warn("userId {} not Exist.", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<Ex_Order_Item> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查 product 是否存在、庫存是否足夠
            if (product == null){
                log.warn("Product {} not Exist.", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);                
            }else if (product.getStock() < buyItem.getQuantity()){
                log.warn("Product {} stock is not enough, Stock ={}, Buy ={}.", buyItem.getProductId(), product.getStock(),buyItem.getQuantity());                
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);                                
            }

            // 扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() -  buyItem.getQuantity());

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
