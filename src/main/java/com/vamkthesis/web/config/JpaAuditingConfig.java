package com.vamkthesis.web.config;

import com.vamkthesis.web.dto.MyUserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("Anonymous");
            }
            String result = "";
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                result = myUserDTO.getEmail();
            }
            return Optional.of(result);
        }
    }
}
