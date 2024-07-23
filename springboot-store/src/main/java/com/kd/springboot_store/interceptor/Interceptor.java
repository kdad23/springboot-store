package com.kd.springboot_store.interceptor;

import com.kd.springboot_store.util.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class Interceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {

        log.info("preHandle ........Interceptor開始.............................");
        // 1.獲取請求url
        String url=request.getRequestURL().toString();
        log.info("這是在Interceptor中--請求的url-----{}" , url);

        // 2.判斷請求url 中是否包含login，如果包含說明是登入操作，直接放行
        if(url.contains("login"))
        {
            log.info("-----登入操作，放行-----");
            return true;
        }
        String jwt=null;
        Cookie settingCookie=null;
        // 去Cookie找上一個request就已經放好的JWT
        Cookie[] mapForCookie= request.getCookies();

        if(mapForCookie != null)
        {
            for(int i=0; i< mapForCookie.length; i++)
            {
                if(mapForCookie[i].getName().equals("tokenFromJava"))
                {
                    // 從Cookie中獲取已生成的jwt
                    // 把存在Cookie中的token賦值給jwt
                    jwt=mapForCookie[i].getValue();
                    // 獲取自己設定的JWT 所在的Cookie
                    settingCookie=mapForCookie[i];
                }
            }
        }
        //4.判斷令牌是否存在，如果不存在，返回錯誤结果（未登入）
        if(!StringUtils.hasLength(jwt))
        {
            log.info("這裡是Interceptor裡面，Token不存在!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return false;
        }
        //5.解析token，如果解析失敗，返回錯誤结果（未登入）
        try
        {
            JwtUtils.parseJWT(jwt);

//            System.out.println("這是解析後的token-----這是在Interceptor中---------" + JwtUtils.parseJWT(jwt));
            log.info("這是解析後的token-----這是在Interceptor中----{}" , JwtUtils.parseJWT(jwt));

            Integer userId = JwtUtils.parseJWT(jwt).get("userId", Integer.class);

            log.info("這裡是Interceptor裡面----目前userId為----{}" , userId);

        }
        catch (Exception e)
        {
            log.info("這裡是Interceptor裡面，這裡是Interceptor裡面，Token解析失敗------------");
            // 還沒有JWT 就自動跳轉到登入頁
//            resp.sendRedirect("/login");
            return false;
        }
        log.info("這裡是Interceptor裡面----Token解析成功");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
