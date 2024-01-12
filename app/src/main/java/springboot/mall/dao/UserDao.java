package springboot.mall.dao;

import springboot.mall.dto.UserRegisterRequest;
import springboot.mall.model.User;

public interface UserDao {
    
    Integer createUser(UserRegisterRequest userRegisterRequest);
    Integer getUserCount(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    User getUserByEmail(String email);
}
