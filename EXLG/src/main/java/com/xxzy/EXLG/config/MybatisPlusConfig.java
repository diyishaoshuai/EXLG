package com.xxzy.EXLG.config;


import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement   //开启事务
@MapperScan("com.xxzy.EXLG.dao")
public class MybatisPlusConfig {
        @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor(){
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(true);  //设置请求的页码大于最大页码,true调回到首页,false继续请求  默认false
        paginationInnerInterceptor.setMaxLimit(500L);  //单页最大数据
        return paginationInnerInterceptor;
    }
}
