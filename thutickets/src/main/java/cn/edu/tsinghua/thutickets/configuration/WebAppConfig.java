package cn.edu.tsinghua.thutickets.configuration;

import cn.edu.tsinghua.thutickets.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginHandlerInterceptor())
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/admin/login");
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:D:\\THU\\2019-2020_Autumn\\4-2 Software Engineering\\hw\\final\\ThuTickets\\thutickets\\images\\");
    }
}
