package com.froso.ufp.core.configuration;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

/**
 * Created by alex on 30.12.13.
 */

@Configuration
        //@ImportResource("classpath:spring-security.xml")
class SecurityConfig {
    /**
     * WARNING; NEVER USE A NOOP PASSWORD ENCODER; A PASSWORD ENCODER IS ALREADY DEFINED IN PROJECT SCOPE
     @Bean public PasswordEncoder passwordEncoder() {
     return NoOpPasswordEncoder.getInstance();
     }
     */
    /**
     * auth_email encoding bean
     *
     * @return the nonce encoder
     */

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(8);
    }
}
