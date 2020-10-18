package com.demo.common;


import cn.hutool.json.JSONUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LiChangChen
 * @Date: 2020/10/14
 */
@Aspect
@Configuration
public class LoggerAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 定义切点Pointcut
     */

    @Pointcut("execution(* com.demo.controller..*.*(..))")
    public void executeControl() {
    }

    @Pointcut("execution(* com.demo.service..*.*(..))")
    public void executeService() {
    }


    @Around("executeControl()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        String params = "";
        //获取请求参数集合并进行遍历拼接
        if (args.length > 0) {
            if ("POST".equals(method)) {
                Object object = args[0];
                Map map = getKeyAndValue(object);
                params = JSONUtil.toJsonStr(map);
            } else if ("GET".equals(method)) {
                params = URLDecoder.decode(queryString, "UTF-8");
            }
        }
        //包名称
        String currentMethodPackage = pjp.getTarget().getClass().getName();
        //方法名称
        String methodName = pjp.getSignature().getName();

        logger.info("请求开始===地址:" + url);
        logger.info("请求开始===类型:" + method);
        logger.info("请求开始===参数:" + params);

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        logger.info("请求结束===包名==>{}==方法名==>{}\n返回值:{}", currentMethodPackage, methodName, result);
        return result;
    }


    @Around("executeService()")
    public Object doAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        //包名称
        String currentMethodPackage = joinPoint.getTarget().getClass().getName();
        //方法名称
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String params = "";
        //获取请求参数集合并进行遍历拼接
        if (args.length > 0) {
            Object object = args[0];
            Map map = getKeyAndValue(object);
            params = JSONUtil.toJsonStr(map);
        }
        logger.info("包名==>{}==方法名==>{}\n返回值:{}", currentMethodPackage, methodName, params);

        // result的值就是被拦截方法的返回值
        Object result = joinPoint.proceed();
        logger.info("包名==>{}==方法名==>{}\n返回值:{}", currentMethodPackage, methodName, result);
        return result;
    }

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = obj.getClass();
        /**
         *  得到类中的所有属性集合
         *  */
        Field[] fs = userCla.getDeclaredFields();
        for (Field f : fs) {
            // 设置些属性是可以访问的
            f.setAccessible(true);
            Object val;
            try {
                val = f.get(obj);
                // 得到此属性的值
                // 设置键值
                map.put(f.getName(), val);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }


}
