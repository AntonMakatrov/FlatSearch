package by.itacademy.user_service.config;

import by.itacademy.user_service.controller.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;

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
                .requestMatchers(POST,"/users/registration").permitAll()
                .requestMatchers(POST,"/users/login").permitAll()
                .requestMatchers(GET,"/users/verification").permitAll()
                .requestMatchers(GET,"/users/me").authenticated()
                .requestMatchers(GET,"/users").hasAnyRole("ADMIN")
                .requestMatchers(GET,"/users/{uuid}").hasAnyRole("ADMIN")
                .requestMatchers(POST,"/users").hasAnyRole("ADMIN")
                .requestMatchers(PUT,"/users/{uuid}").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
        );

        http
                .addFilterBefore(filter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
