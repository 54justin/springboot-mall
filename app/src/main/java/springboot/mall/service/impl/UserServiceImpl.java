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

    //�K�[log
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    public User getUserById(Integer userId){
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest){
        // �ˬd���U��email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null){
            log.warn("Email {} has been registered.", userRegisterRequest.getEmail()); // {}���e���Ѽ�
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);  //Email�w�s�b�A�^400
        }

        // �ϥ� MD5 �ͦ��K�X�������
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        // �Ыرb��
        return userDao.createUser(userRegisterRequest);
    }

    public User login(UserLoginRequest userLoginRequest){

        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // �ˬd user�O�_�s�b
        if (user == null){
            log.warn("Email {} has not been registered.", userLoginRequest.getEmail()); // {}���e���Ѽ�
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);  //Email�w�s�b�A�^400
        }
        
        // �ϥ� MD5 �ͦ��K�X�������
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        // ����K�X
        if (user.getPassword().equals(hashedPassword)){
            return user;
        }else{
            log.warn("Email {} password is not correct.", userLoginRequest.getEmail()); // {}���e���Ѽ�
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);  //Email�w�s�b�A�^400            
        }
    }    
}