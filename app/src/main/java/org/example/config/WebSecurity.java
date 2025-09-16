package org.example.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@EnableWebSecurity
public class WebSecurity {
    private final UserDetailsService userDetailsService;
    private final JWTFilter jwtFilter;

    WebSecurity(UserDetailsService userDetailsService, JWTFilter jwtFilter){
        this.userDetailsService=userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.cors(Customizer.withDefaults()).
                csrf(csrf->csrf.disable()).
                authorizeHttpRequests(
                        request-> request.
                                requestMatchers("/api/register", "/api/login", "/register", "/login").
                                permitAll().
                                requestMatchers("/api/products/**","/api/orders/**", "/api/customers/**").
                                hasAnyRole("ADMIN","SALESSTAFF").
                                requestMatchers("/api/inventory/**").
                                hasAnyRole("ADMIN","WAREHOUSESTAFF").
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

