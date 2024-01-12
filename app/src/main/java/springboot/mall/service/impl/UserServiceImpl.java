package springboot.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import springboot.mall.dao.UserDao;
import springboot.mall.dto.UserRegisterRequest;
import springboot.mall.model.User;
import springboot.mall.service.UserService;

@Component
public class UserServiceImpl implements UserService{
 
    @Autowired
    private UserDao userDao;

    public Integer register(UserRegisterRequest userRegisterRequest){
        return userDao.createUser(userRegisterRequest);
    }

    public User getUserById(Integer userId){
        return userDao.getUserById(userId);
    }
}
