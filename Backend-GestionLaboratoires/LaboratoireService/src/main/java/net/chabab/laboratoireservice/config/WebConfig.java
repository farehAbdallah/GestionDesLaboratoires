//package net.chabab.laboratoireservice.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")  // Allow CORS on all API endpoints
//                .allowedOrigins("http://localhost:4200")  // Allow frontend origin
//                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow specific methods
//                .allowedHeaders("*")  // Allow all headers
//                .allowCredentials(true);  // Allow credentials if needed
//    }
//}
