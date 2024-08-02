package com.kd.springboot_store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kd.springboot_store.dao.UserDao;
import com.kd.springboot_store.dto.UserRegisterRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;



    private ObjectMapper objectMapper = new ObjectMapper();



    // 測試註冊帳號是否能成功
    @Transactional
    @Test
    public void registerSuccess() throws Exception {

        // 先註冊新帳號
        UserRegisterRequestDTO userRegisterRequestDTO = new UserRegisterRequestDTO();
        userRegisterRequestDTO.setEmail("store666@gmail.com");
        userRegisterRequestDTO.setPassword("111111");


        String json = objectMapper.writeValueAsString(userRegisterRequestDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.email", equalTo("dddddddddd@gmail.com")))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue())).
                andExpect(jsonPath("$.accessToken", notNullValue()));

        // 檢查資料庫中的密碼不為明碼
//        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
//        assertNotEquals(userRegisterRequest.getPassword(), user.getPassword());




    }













}