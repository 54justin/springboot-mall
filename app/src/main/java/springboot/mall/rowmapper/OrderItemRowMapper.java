package springboot.mall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import springboot.mall.model.Ex_Order_Item;

public class OrderItemRowMapper implements RowMapper<Ex_Order_Item>{

    @Override
    public Ex_Order_Item mapRow(ResultSet resultSet, int i) throws SQLException{
        Ex_Order_Item orderItem = new Ex_Order_Item();
        orderItem.setOrderItemId(resultSet.getInt("order_item_id"));
        orderItem.setOrderId(resultSet.getInt("order_id"));
        orderItem.setProductId(resultSet.getInt("product_id"));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        orderItem.setAmount(resultSet.getInt("amount"));                        

        orderItem.setProductName(resultSet.getString("product_name"));
        orderItem.setImageUrl(resultSet.getString("image_url"));        

        return orderItem;
    }    
}