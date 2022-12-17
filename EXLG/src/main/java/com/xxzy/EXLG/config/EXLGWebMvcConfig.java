package com.xxzy.EXLG.config;


import com.xxzy.EXLG.interceptor.LoginUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author gjq0117
 * @email  gjq0117@163.com
 * @date   2022/4/23 上午 11:38
 */
@Configuration
public class EXLGWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginUserInterceptor loginUserInterceptor;

    /**
     * 拦截器  默认拦截所有请求
     * 放行 地址请求 和 swaggerAPI请求
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginUserInterceptor).addPathPatterns("/**").excludePathPatterns("/province/**","/city/**","/county/**",
                "/swagger-ui.html/**","/swagger-resources/**","/v3/api-docs","/webjars/**","/v2/**"); //,"/user/**"
    }
}
