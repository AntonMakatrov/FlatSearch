package by.itacademy.audit.config;

import by.itacademy.audit.controller.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception  {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        http
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(
                                        (request, response, ex) -> response.setStatus(
                                                HttpServletResponse.SC_UNAUTHORIZED
                                        )
                                )
                                .accessDeniedHandler(
                                        (request, response, ex) -> response.setStatus(
                                                HttpServletResponse.SC_FORBIDDEN
                                        )
                                )
                );
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(GET, "/audit").hasAnyRole("ADMIN")
                .requestMatchers(GET, "/audit/{uuid}").hasAnyRole("ADMIN")
                .requestMatchers(POST, "/report/{type}").permitAll()
                .requestMatchers(GET, "/report/{uuid}/export").permitAll()
                .requestMatchers(HEAD, "/report/{uuid}/export").permitAll()
                .requestMatchers(GET, "/report").permitAll()
                .requestMatchers(POST, "/audit").hasAnyRole("SYSTEM")
        );
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
