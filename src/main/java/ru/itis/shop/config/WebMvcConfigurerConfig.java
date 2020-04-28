//package ru.itis.shop.config;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@EnableWebMvc
//@Component
//public class WebMvcConfigurerConfig implements WebMvcConfigurer {
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
//
//    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/js/**")
//                .addResourceLocations("../webapp/resources/js/")
//                .setCachePeriod(31556926);
//    }
//
//
//}
