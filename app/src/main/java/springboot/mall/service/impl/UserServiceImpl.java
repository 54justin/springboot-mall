package springboot.mall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import springboot.mall.dao.UserDao;
import springboot.mall.dto.UserRegisterRequest;
import springboot.mall.model.User;
import springboot.mall.service.UserService;

@Component
public class UserServiceImpl implements UserService{

    //添加log
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    public User getUserById(Integer userId){
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest){
        // 檢查註冊的email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null){
            log.warn("Email {} has been registered.", userRegisterRequest.getEmail()); // {}內容為參數
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);  //Email已存在，回400
        }
        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }
}
