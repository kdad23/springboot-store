package com.kd.springboot_store.service.impl;

import com.kd.springboot_store.dao.UserRepository;
import com.kd.springboot_store.dto.UserLoginRequestDTO;
import com.kd.springboot_store.dto.UserRegisterRequestDTO;
import com.kd.springboot_store.dto.UserResponseDTO;
import com.kd.springboot_store.model.User;
import com.kd.springboot_store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    UserRepository userRepository;

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

        User user=new User();
        BeanUtils.copyProperties(userRegisterRequestDTO, user);
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
        if(user.getPassword().equals(hashedPassword))
        {
            BeanUtils.copyProperties(user, userResponseDTO);
            return userResponseDTO;
        }
        else
        {
            log.warn("該 email {} 的密碼不正確", userLoginRequestDTO.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }
    }



}