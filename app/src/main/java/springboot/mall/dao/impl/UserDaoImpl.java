package springboot.mall.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import springboot.mall.dao.ProductQueryParams;
import springboot.mall.dao.UserDao;
import springboot.mall.dto.UserRegisterRequest;
import springboot.mall.model.Product;
import springboot.mall.model.User;
import springboot.mall.rowmapper.ProductRowMapper;
import springboot.mall.rowmapper.UserRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class UserDaoImpl implements UserDao {
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate JdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest){
        String sql = "INSERT INTO useracct(email, password, created_date, last_modified_date) VALUES (:email, :password, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());                             
        
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        // 使KeyHolder取得流水號(自動生成的ID)  --MySql用，Oracle會有錯誤
        //KeyHolder keyHolder = new GeneratedKeyHolder();
        //namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        namedParameterJdbcTemplate.update(sql, map);

        Integer userId = this.getUserCount(null);

        return userId;
    }

    @Override
    public Integer getUserCount(UserRegisterRequest userRegisterRequest){
        String sql = "SELECT COUNT(*) FROM useracct WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        Integer total = JdbcTemplate.queryForObject(sql, Integer.class);
        return total;
    }
    
    @Override
    public User getUserById(Integer userId){
        String sql = "SELECT user_id, email, password, created_date, last_modified_date FROM useracct WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());        

        if (userList.size() > 0){
            return userList.get(0);
        }else{
            return null;
        }
    }   
}
