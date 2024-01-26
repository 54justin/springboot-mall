package springboot.mall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import springboot.mall.dao.UserDao;
import springboot.mall.dto.UserLoginRequest;
import springboot.mall.dto.UserRegisterRequest;
import springboot.mall.model.User;
import springboot.mall.service.UserService;

@Component
public class UserServiceImpl implements UserService{

    //睰log
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    public User getUserById(Integer userId){
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest){
        // 浪琩爹email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null){
            log.warn("Email {} has been registered.", userRegisterRequest.getEmail()); // {}ず甧把计
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);  //Email400
        }
        // 承眀腹
        return userDao.createUser(userRegisterRequest);
    }

    public User login(UserLoginRequest userLoginRequest){

        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        if (user == null){
            log.warn("Email {} has not been registered.", userLoginRequest.getEmail()); // {}ず甧把计
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);  //Email400
        }
        
        if (user.getPassword().equals(userLoginRequest.getPassword())){
            return user;
        }else{
            log.warn("Email {} password is not correct.", userLoginRequest.getEmail()); // {}ず甧把计
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);  //Email400            
        }
    }    
}
