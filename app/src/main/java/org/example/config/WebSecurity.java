package org.example.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurity {
    private final UserDetailsService userDetailsService;
    private final JWTFilter jwtFilter;

    WebSecurity(UserDetailsService userDetailsService, JWTFilter jwtFilter){
        this.userDetailsService=userDetailsService;
        this.jwtFilter = jwtFilter;
    }
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration=new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.cors(Customizer.withDefaults()).
                csrf(csrf->csrf.disable()).
                authorizeHttpRequests(
                        request-> request.
                                requestMatchers("/api/register", "/api/login", "/register", "/login").
                                permitAll().
                                requestMatchers("/api/users").
                                hasAnyAuthority("ROLE_ADMIN", "ROLE_SALESSTAFF").
                                requestMatchers("/api/products/**").
                                hasAnyAuthority("ROLE_ADMIN","ROLE_SALESSTAFF", "ROLE_WAREHOUSESTAFF").
                                requestMatchers("/api/orders/**", "/api/customers/**").
                                hasAnyAuthority("ROLE_ADMIN","ROLE_SALESSTAFF").
                                // Allow SALESSTAFF to view inventory (read-only)
                                requestMatchers(org.springframework.http.HttpMethod.GET, "/api/inventory/**").
                                hasAnyAuthority("ROLE_ADMIN", "ROLE_WAREHOUSESTAFF", "ROLE_SALESSTAFF").
                                requestMatchers("/api/inventory/**").
                                hasAnyAuthority("ROLE_ADMIN","ROLE_WAREHOUSESTAFF").
                                requestMatchers(HttpMethod.GET, "/api/notifications/user/{userId}/**").
                                authenticated(). // Any authenticated user can check their notifications
                                anyRequest().
                                authenticated()
                ).
                addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).
                sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(14);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}

