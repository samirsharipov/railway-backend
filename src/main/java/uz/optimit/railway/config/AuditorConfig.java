package uz.optimit.railway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.optimit.railway.entity.User;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AuditorConfig {

    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }
            User currentUser = (User) authentication.getPrincipal();
            return Optional.ofNullable(currentUser.getId());
        };
    }
}
