package springboot.mall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import springboot.mall.model.Ex_Order;
import org.springframework.jdbc.core.RowMapper;

public class OrderRowMapper implements RowMapper<Ex_Order>{

    @Override
    public Ex_Order mapRow(ResultSet resultSet, int i) throws SQLException{
        Ex_Order order = new Ex_Order();
        order.setOrderId(resultSet.getInt("order_id"));
        order.setUserId(resultSet.getInt("user_id"));
        order.setTotalAmount(resultSet.getInt("total_amount"));
        order.setCreatedDate(resultSet.getTimestamp("created_date"));
        order.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));                        

        return order;
    }
    
}
