package com.kd.springboot_store.service.impl;

import com.kd.springboot_store.dao.UserRepository;
import com.kd.springboot_store.dto.*;
import com.kd.springboot_store.model.LoginUser;
import com.kd.springboot_store.model.Product;
import com.kd.springboot_store.model.User;
import com.kd.springboot_store.service.UserService;
import com.kd.springboot_store.util.BeanCopyUtils;
import com.kd.springboot_store.util.JwtUtil;
import com.kd.springboot_store.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    UserRepository userRepository;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Override
    public UserResponseDTO getUserById(Integer userId)
    {
        User user=userRepository.findById(userId).orElse(null);

        if(user != null)
        {
            System.out.println(user.getCreatedDate());
            UserResponseDTO userResponseDTO=new UserResponseDTO();
            BeanUtils.copyProperties(user, userResponseDTO);
            return userResponseDTO;
        }
        else
        {
            return null;
        }

    }

    @Override
    public Integer register(UserRegisterRequestDTO userRegisterRequestDTO)
    {
        // 密碼用BCrypt加密
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        userRegisterRequestDTO.setPassword(bCryptPasswordEncoder.encode(userRegisterRequestDTO.getPassword()));
        User user=new User();
        BeanUtils.copyProperties(userRegisterRequestDTO, user);
        user.setRoles("USER");
        userRepository.save(user);
        Integer userId=
                userRepository.findById(user.getUserId()).orElse(null).getUserId();

        return userId;
    }

    @Override
    public UserResponseDTO login(UserLoginRequestDTO userLoginRequestDTO)
    {
        // 檢查註冊的email
        User user=userRepository.findByEmail(userLoginRequestDTO.getEmail());

        UserResponseDTO userResponseDTO=new UserResponseDTO();

        // 檢查user 是否存在
        if (user == null)
        {
            log.warn("該 email {} 尚未註冊", userLoginRequestDTO.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword=
                DigestUtils.md5DigestAsHex(userLoginRequestDTO.getPassword().getBytes());

        // 比較密碼
        if(user.getPassword().equals(userLoginRequestDTO.getPassword()))
        {
            BeanUtils.copyProperties(user, userResponseDTO);
            return userResponseDTO;
        }
        else
        {
            log.warn("該 email {} 的密碼不正確", userLoginRequestDTO.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }



//        if(user.getPassword().equals(hashedPassword))
//        {
//            BeanUtils.copyProperties(user, userResponseDTO);
//            return userResponseDTO;
//        }
//        else
//        {
//            log.warn("該 email {} 的密碼不正確", userLoginRequestDTO.getEmail());
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//
//        }






    }


    @Override
    public List<UserResponseDTO> getAllUser()
    {

        List<User> userList=userRepository.findAll();
        List<UserResponseDTO> userResponseDTOList= BeanCopyUtils.copyListProperties(userList
                , UserResponseDTO::new);
        if (userResponseDTOList.size() > 0)
        {

            return userResponseDTOList;
        }
        else
        {
            return null;
        }

    }


    @Override
    public void updateUser(Integer userId, UserRequestDTO userRequestDTO)
    {

        User user=new User();
        BeanUtils.copyProperties(userRequestDTO, user);

        // 從資料庫裡取出的資料
        User user2=
                userRepository.findById(userId).orElse(null);

        if (user2 != null)
        {
            // 更新用戶的更新時間
            user2.setLastModifiedDate(new Date());
            user2.setRoles(userRequestDTO.getRoles());
//            BeanUtils.copyProperties(user2, user2);
            userRepository.save(user2);
        }

    }


    @Override
    public Integer registerForSpringSecurity(UserRegisterRequestDTO userRegisterRequestDTO)
    {
        // 密碼用BCrypt加密
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        userRegisterRequestDTO.setPassword(bCryptPasswordEncoder.encode(userRegisterRequestDTO.getPassword()));
        User user=new User();
        BeanUtils.copyProperties(userRegisterRequestDTO, user);
        user.setRoles("USER");
        userRepository.save(user);
        Integer userId=
                userRepository.findById(user.getUserId()).orElse(null).getUserId();
//        //1.封装Authentication对象，這裡放入email，後面的ProviderManager就會調用UserdetailServiceImpl
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
//
//        //2.通过AuthenticationManager的authenticate方法来进行用户认证
//        Authentication authenticated = authenticationManager.authenticate(authenticationToken);
//
//        // 如果驗證沒通過，給出對應的提示
//        if(Objects.isNull(authenticated))
//        {
//            throw new RuntimeException("登入失敗");
//        }
//        // 如果認證通過了，使用userId生成一個Jwt，Jwt存入ResponseResult返回
//        //3.在Authentication中获取用户信息
//        LoginUser loginUser = (LoginUser) authenticated.getPrincipal();
//
//        String userId2 = loginUser.getUser().getUserId().toString();
//        //4.认证通过生成token
//        String jwt = JwtUtil.createJWT(userId2);
//        //5.用户信息存入redis，存入的物件型態為LoginUser
//        //6.把token返回给前端
//        HashMap<Object, Object> hashMap = new HashMap<>();
//        hashMap.put("token", jwt);
//        // 把完整的用戶訊息存入redis userId作為key
//        redisCache.setCacheObject("login:" + userId, loginUser);
//
//        UserResponseDTO userResponseDTO=new UserResponseDTO();
//        BeanUtils.copyProperties(loginUser.getUser(), userResponseDTO);
//        userResponseDTO.setAccessToken(jwt);

        return userId;
    }
}
