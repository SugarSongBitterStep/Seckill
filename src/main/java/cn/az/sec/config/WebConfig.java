package cn.az.sec.config;

import cn.az.sec.filter.SessionExpireFilter;
import cn.az.sec.interceptor.AuthorityInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * @author az
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Resource
    private AuthorityInterceptor authorityInterceptor;

    /**
     * Override this method to add resource handlers for serving static resources.
     * 若不重载 => 不加载静态资源
     *
     * @param registry registry
     * @see ResourceHandlerRegistry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        // 映射为 user 的控制器下的所有映射
        registry.addInterceptor(authorityInterceptor);
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
