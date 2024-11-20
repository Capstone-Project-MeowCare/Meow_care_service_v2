package com.meow_care.meow_care_service.configurations;

import com.meow_care.meow_care_service.util.UserUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.lang.NonNull;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditorConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}

class AuditorAwareImpl  implements AuditorAware<String> {
    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return Optional.of(UserUtils.getCurrentUserEmail());
    }
}
