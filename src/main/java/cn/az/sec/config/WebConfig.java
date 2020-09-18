package cn.az.sec.config;

import cn.az.sec.filter.SessionExpireFilter;
import cn.az.sec.interceptor.AuthorityInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * @author az
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Resource
    AuthorityInterceptor authorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        // 映射为 user 的控制器下的所有映射
        registry.addInterceptor(authorityInterceptor)
                .addPathPatterns("/user/login")
                .excludePathPatterns("/index", "/");
        super.addInterceptors(registry);
    }

    @Bean("myFilter")
    public Filter uploadFilter() {
        return new SessionExpireFilter();
    }

    @Bean
    public FilterRegistrationBean<?> uploadFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new DelegatingFilterProxy("myFilter"));
        registration.addUrlPatterns("/**");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }

}
