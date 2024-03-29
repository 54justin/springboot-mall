package springboot.mall.service;

import springboot.mall.dto.UserLoginRequest;
import springboot.mall.dto.UserRegisterRequest;
import springboot.mall.model.User;

public interface UserService {
    
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User login(UserLoginRequest userLoginRequest); 
}
