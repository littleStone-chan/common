package com.o2osys.tools.config.interceptor;


import com.o2osys.tools.interceptor.TokenAuthcInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ConfigurationProperties(prefix = "o2osys.interceptor")
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private String[] excludes;

    @Bean
    public TokenAuthcInterceptor tokenAuthcInterceptor() {
        return new TokenAuthcInterceptor();
    }


    /**
     * <p>Title:</p>
     * <p>Description:重写增加自定义拦截器的注册，某一个拦截器需要先注册进来，才能工作</p>
     * param[1]: null
     * return:
     * exception:
     * date:2018/4/18 0018 下午 17:29
     * author:段美林[duanml@neusoft.com]
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(tokenAuthcInterceptor());

        // swagger相关放行
        addInterceptor.excludePathPatterns(
                "/doc.html","/swagger/**","/v2/api-docs","/swagger-ui.html",
                "/swagger-resources/**","/captcha.jpg","/webjars/**");

        // 排除配置
        if (ObjectUtils.isEmpty(excludes)){
            addInterceptor.excludePathPatterns();
        }else {
            addInterceptor.excludePathPatterns(excludes);
        }

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    public String[] getExcludes() {
        return excludes;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }
}
