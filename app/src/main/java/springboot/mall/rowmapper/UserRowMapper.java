package springboot.mall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import springboot.mall.model.User;
import org.springframework.jdbc.core.RowMapper;

// 把DB的結果轉成Object
public class UserRowMapper implements RowMapper<User>{

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User User = new User();
        User.setUserId(resultSet.getInt("user_id"));
        User.setEmail(resultSet.getString("email"));
        User.setPassword(resultSet.getString("password"));
        User.setCreatedDate(resultSet.getTimestamp("created_date"));
        User.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));                

        return User;
    }    
}
