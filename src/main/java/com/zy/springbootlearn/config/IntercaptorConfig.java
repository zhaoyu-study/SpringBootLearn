package com.zy.springbootlearn.config;

import com.zy.springbootlearn.config.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 将拦截器注入到springMVC
 */
@Configuration//所有的配置类要加Configuration
public class IntercaptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor())//这里如果时直接new出 interceptor() 的话，不会将其放到容器中，后面拿不到
                    //拦截的路径，拦截所有请求，通过判断token是否合法来决定是否需要登录
                .addPathPatterns("/**")
                    //排除接口
                .excludePathPatterns("/user/login","/user/register","/**/export", "/**/import", "/file/upload","/file/download/**");

    }

    //注入springboot到容器中
    @Bean
    public JWTInterceptor interceptor(){
        return new JWTInterceptor();
    }
}
