package com.elsonkhoza.ecommerce001.security;


import com.elsonkhoza.ecommerce001.user.JpaUserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JpaUserSecurityService userSecurityService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   HandlerMappingIntrospector handlerMappingIntrospector
    ) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder
                = new MvcRequestMatcher.Builder(handlerMappingIntrospector);

        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers(mvcMatcherBuilder.pattern("/h2-console/**"))
                        .disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(mvcMatcherBuilder.pattern("/h2-console/**"))
                                .permitAll()
                                .anyRequest()
                                .permitAll()
                )
                .authenticationProvider(authenticationProvider())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userSecurityService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
