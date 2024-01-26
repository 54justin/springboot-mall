package springboot.mall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import springboot.mall.dao.UserDao;
import springboot.mall.dto.UserLoginRequest;
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

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    public User login(UserLoginRequest userLoginRequest){

        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // 檢查 user是否存在
        if (user == null){
            log.warn("Email {} has not been registered.", userLoginRequest.getEmail()); // {}內容為參數
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);  //Email已存在，回400
        }
        
        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        // 比較密碼
        if (user.getPassword().equals(hashedPassword)){
            return user;
        }else{
            log.warn("Email {} password is not correct.", userLoginRequest.getEmail()); // {}內容為參數
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);  //Email已存在，回400            
        }
    }    
}
