package springboot.mall.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import springboot.mall.dao.OrderDao;
import springboot.mall.model.Ex_Order;
import springboot.mall.model.Ex_Order_Item;
import springboot.mall.rowmapper.OrderItemRowMapper;
import springboot.mall.rowmapper.OrderRowMapper;

@Component
public class OrderDaoImpl implements OrderDao{
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount){
        String sql = "INSERT INTO ex_order(user_id,total_amount,created_date,last_modified_date) VALUES (:userid, :totalAmount, :createDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("totalAmount", totalAmount);

        Date now = new Date();
        map.put("createDate", now);
        map.put("lastModifiedDate", now);        

        // 課程範例(使用MySQL)
        // KeyHolder keyHolder = new GeneratedKeyHolder();
        // namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);      
        // int orderId = keyHolder.getKey().intValue();
        
        // 使用Oracle
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
        // 在這種情況下，不再需要使用 GeneratedKeyHolder
        // 因為主鍵值是在插入時由序列生成的 
        // 如果您還需要返回新插入的行的 ID，您可以從序列中獲取它
        int orderId = jdbcTemplate.queryForObject("SELECT order_seq.CURRVAL FROM DUAL", Integer.class);

        return orderId;
    }

    public void createOrderItems(Integer orderId, List<Ex_Order_Item> orderItemList){
        
        // 使用for loop 一條一條 sql 加入數據，效率較低
        // for (Ex_Order_Item orderItem:orderItemList){
        //     String sql = "INSERT INTO ex_order_item(order_id, product_id, quantity, amount) VALUES (:orderId, :productId, :quantity, :amount)";            

        //     Map<String, Object> map = new HashMap<>();
        //     map.put("orderId", orderId);
        //     map.put("productId", orderItem.getProductId());

        //     map.put("quantity", orderItem.getQuantity());
        //     map.put("amount", orderItem.getAmount()); 

        //     namedParameterJdbcTemplate.update(sql, map);
        // }
        
        // 使用 batchUpdate 一次性加入數據，效率更高
        String sql = "INSERT INTO ex_order_item(order_id, product_id, quantity, amount) " +
                     "VALUES (:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i =0; i < orderItemList.size(); i++){
            Ex_Order_Item orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());                   
            parameterSources[i].addValue("quantity", orderItem.getQuantity());                   
            parameterSources[i].addValue("amount", orderItem.getAmount());                                           
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    public Ex_Order getOrderById(Integer orderId){
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                     "FROM ex_order WHERE order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Ex_Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (orderList.size() > 0){
            return orderList.get(0);
        }else{
            return null;
        }
    }

    public List<Ex_Order_Item> getOrderItemsByOrderId(Integer orderId){
        String sql = "SELECT o.order_item_id, o.order_id, o.product_id, o.quantity, o.amount, p.product_name, p.image_url " +
                     "FROM ex_order_item o, product p " +
                     "WHERE o.product_id = p.product_id and o.order_id = :orderId ";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Ex_Order_Item> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }
}
