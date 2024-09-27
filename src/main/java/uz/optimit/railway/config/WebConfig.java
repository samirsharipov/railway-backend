package uz.optimit.railway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Barcha yo'llar uchun CORSni o'rnatish
                .allowedOrigins("*") // Barcha domenlarga ruxsat berish
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Ruxsat berilgan HTTP metodlari
                .allowedHeaders("*") // Barcha headerlarga ruxsat berish
                .allowCredentials(false) // Cookie'larni yoqish yoki o'chirish
                .maxAge(3600); // Pre-flight request'lar uchun kesh vaqti
    }

}
