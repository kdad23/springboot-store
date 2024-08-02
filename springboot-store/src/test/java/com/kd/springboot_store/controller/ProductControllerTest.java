package com.kd.springboot_store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kd.springboot_store.dto.UserLoginRequestDTO;
import com.kd.springboot_store.dto.UserRegisterRequestDTO;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    // 登入
    @Transactional
    @Test
    public void loginSuccess() throws Exception {
        // 先註冊新帳號
        UserRegisterRequestDTO userRegisterRequestDTO = new UserRegisterRequestDTO();
        userRegisterRequestDTO.setEmail("store555@gmail.com");
        userRegisterRequestDTO.setPassword("111111");

        register(userRegisterRequestDTO);

        // 再測試登入功能
        UserLoginRequestDTO userLoginRequest = new UserLoginRequestDTO();
        userLoginRequest.setEmail(userRegisterRequestDTO.getEmail());
        userLoginRequest.setPassword(userRegisterRequestDTO.getPassword());

        String json = objectMapper.writeValueAsString(userRegisterRequestDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
//                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.email", equalTo(userLoginRequest.getEmail())))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }


    // 查詢商品
    @Transactional
    @Test
    public void getNotesSuccess() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/products");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
        ;

    }





    private void register(UserRegisterRequestDTO userRegisterRequestDTO) throws Exception {
        String json = objectMapper.writeValueAsString(userRegisterRequestDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
    }














}