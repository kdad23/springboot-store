package com.kd.springboot_store.config;


import com.kd.springboot_store.filter.JwtAuthenticationTokenFilter;
import com.kd.springboot_store.handler.AuthenticationEntryPointImpl;
import com.kd.springboot_store.service.impl.LoginServiceImpl;
import com.kd.springboot_store.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //配置類
//@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity // 开启Spring Security的功能 代替了 implements WebSecurityConfigurerAdapter
public class SecurityConfig {

    //獲取AuthenticationManager
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    AccessDeniedHandler accessDeniedHandler;
    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    // 密码加密存储模式更改
    // 把BCryptPasswordEncoder对象注入Spring容器中，SpringSecurity就会使用该PasswordEncoder来进行密码验。
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }






//    @Bean
//    public AuthenticationManager authenticationManager() {
//        return new ProviderManager(daoAuthenticationProvider());
//        // 若有多個驗證方式，可使用：
//        // List.of(daoAuthenticationProvider(), customAuthenticationProvider())
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder){
//        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
////将编写的UserDetailsService注入进来
//        provider.setUserDetailsService(userDetailsService);
////将使用的密码编译器加入进来
//        provider.setPasswordEncoder(passwordEncoder);
////将provider放置到AuthenticationManager 中
//        ProviderManager providerManager=new ProviderManager(provider);
//        return providerManager;
//    }



//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider
//                = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
//        return authenticationProvider;
//    }









    /**
     * 配置Spring Security的過濾鍊。
     *
     * @param http 用於構建安全配置的HttpSecurity物件。
     * @return 返回配置好的SecurityFilterChain物件。
     * @throws Exception 如果配置過程中發生錯誤，則抛出異常。
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF保護
                .csrf(csrf -> csrf.disable())
                // 設定會話創建策略為無狀態
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 設定授權規則
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/users/register").permitAll()
                        .requestMatchers("/api/users/login").permitAll()
                        .requestMatchers("/api/users/logout").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/**").permitAll()
                        .requestMatchers("/api/users/orders").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                //開啟跨域訪問
                .cors(AbstractHttpConfigurer::disable)
                // 添加JWT認證過濾器
                .addFilterBefore(jwtAuthenticationTokenFilter,
                        UsernamePasswordAuthenticationFilter.class)
                // 配置異常處理
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint));


        // 構建並返回安全過濾鍊
        return http.build();
    }


}








