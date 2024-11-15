package com.meow_care.meow_care_service.configurations;

import com.meow_care.meow_care_service.util.UserUtils;
import io.micrometer.common.lang.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
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
