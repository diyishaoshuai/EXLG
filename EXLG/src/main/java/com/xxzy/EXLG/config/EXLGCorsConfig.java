package com.xxzy.EXLG.config;

import com.xxzy.EXLG.interceptor.LoginUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022/4/23 上午 11:36
 */

//解决跨域问题
    //方式一:  配置过滤器 CorsFilter  (如果以配置过滤器的形式解决跨域问题就会出现下面的"坑")
    //方式二:  配置拦截器 WebMvcConfigurationSupport  (本项目中用的)
    //方式三:  代理服务器 Nginx等

/**
 * TODO:  踩坑: ===>  配置了CorsFilter解决跨域问题之后,又配置了Interceptor拦截器(本项目中用于解决用户登陆权限问题),导致CorsFilter失效
 *             原因:  解决跨域问题的本质是在response中设置请求头信息,CorsFilter就是基于这个原理封装了一个filter实现了解决全局跨域问题,
 *                   但是我们知道过滤器的执行是优先于拦截器的执行的,而分析源码可知拦截器在拦截到请求之后会执行response.reset(),即重置了response,
 *                   因此在配置了拦截器之后,导致解决全局跨域的filter失效。
 *             解决:  以拦截器的形式解决跨域问题
 *  拦截器和过滤器的区别:
 *     1.过滤器是实现servlet的规范,仅适用于web应用
 *     2.拦截器是spring家提供的一个组件,归spring管理,不仅仅局限于web应用,还支持application swing等
 */

@Configuration
public class EXLGCorsConfig{
    @Bean
    public CorsFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //配置跨域
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}

