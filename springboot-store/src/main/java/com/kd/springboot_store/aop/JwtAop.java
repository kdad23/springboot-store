package com.kd.springboot_store.aop;

//import com.kd.springboot_store.util.createNewJwt;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//通知放在切面類中的方法裡面
//@Slf4j
//@Component
//@Aspect
//public class JwtAop
//{
//
//    /**
//     * 	annotation 切入點表達式:
//     基於註解的方式來搭配切入點法。這種方式雖然多一步操作
//     ，我們需要自訂一個註解，但是相對來比較靈活。
//     我們需要配對哪個方法，就在方法上加上對應的註解就可以了
//     */
//    // @Pointcut 聲明切入點表達式的註解，把切入點表達式抽取出來
//    // public 的方法才可以讓這個切入點表達式在別的切面Class使用
//    @Pointcut("@annotation(com.jason.springboot_mall.aop.ReNewJwt)")
//    private void pt(){}
//
//
//
//    @Autowired
//    private HttpServletRequest req;
//    @Autowired
//    private HttpServletResponse resp;
//
//    // around(ProceedingJoinPoint pjp)為通知方法
//    //  通知：Advice，指哪些重複的邏輯，也就是共通性功能（最終體現為一個方法）
//    //環繞通知
//    @Around("pt()")
//    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//
//        log.info("----JwtAop啟用-------------------");
//        log.info("這是@Around，--------around before 開始..................");
//        //紀錄方法執行開始時間
//        long begin = System.currentTimeMillis();
//
//        String jwt=null;
////        Cookie settingCookie=new Cookie("tokenFromJava", "valuee");
//        Cookie settingCookie=null;
//
//        Cookie[] mapForCookie= req.getCookies();
//        if (mapForCookie != null)
//        {
//            for(int i=0; i< mapForCookie.length; i++)
//            {
//
//                if(mapForCookie[i].getName().equals("tokenFromJava"))
//                {
//                    // 從Cookie中獲取已生成的jwt
//                    // 把 存在Cookie中的token賦值給jwt
//                    jwt=mapForCookie[i].getValue();
//
//                    // 獲取自己設定的JWT 所在的Cookie
//                    settingCookie=mapForCookie[i];
//                }
//            }
//        }
//        log.info("舊的Jwt-----{}" , jwt);
//
//        // 刪除Cookie
//        settingCookie.setMaxAge(0);
//
//        settingCookie.setPath("/");//该路径为创建Cookie时设定的访问路径
//        settingCookie.setHttpOnly(true);
//        resp.addCookie(settingCookie);
//
//        // 設置1個新jwt 在瀏覽器的cookie上
//        Cookie myCookie = new Cookie("tokenFromJava", createNewJwt.giveYouNewJWT(req));
//        myCookie.setHttpOnly(true);
//        resp.addCookie(myCookie);
//
//        //獲取目標類名
//        String name = pjp.getTarget().getClass().getName();
//        log.info( "這是@Around，目標類名：{}" , name);
//
//        //目標方法名
//        String methodName = pjp.getSignature().getName();
//        log.info("這是 @Around，目標方法名：{}" ,methodName);
//
//        //執行原始方法
//        Object returnValue = pjp.proceed();
//        log.info("這是@Around，around after 開始... ............................");
//
//        //紀錄方法執行結束時間
//        long end = System.currentTimeMillis();
//        long totalTime=end-begin;
//        log.info("執行"+ methodName + "方法花了" + totalTime + "毫秒");
//
//        return returnValue;
//
//    }
//
//
//
//}
