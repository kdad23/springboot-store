package com.kd.springboot_store.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Component
public class RedisUtils {
    // 启动Redis服务器
    public static void startRedisServer() {
        try {
            Process process = Runtime.getRuntime().exec("D:\\學習程式的檔案\\redis\\Redis-x64-3.0.504\\redis.windows.conf");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 登录到Redis服务器
    public static void loginRedisCli(String host, int port, String password) {
        try {
            String command = "redis-cli.exe -h " + host + " -p " + port + " -a " + password;
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 启动Redis服务器
        startRedisServer();

        // 登录到Redis服务器
        loginRedisCli("localhost", 6379, "123456");
    }
}
