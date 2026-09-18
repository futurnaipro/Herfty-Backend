package artisanat.artisanat.Security;



import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import artisanat.artisanat.model.DTOs.JwtRequestDTO;
import artisanat.artisanat.model.Filters.JwtAuthenticationFilter;
import artisanat.artisanat.model.Services.JwtTokenService;
import artisanat.artisanat.model.Services.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private UserDetailsServiceImp userDetailsService;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(UserDetailsServiceImp userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

         http.csrf(c->c.disable());
         
         http.authorizeHttpRequests(req -> req.requestMatchers("/auth/**","/add/**","/articles/**","/article/**").permitAll()
         .requestMatchers("/artisan/**").hasAuthority("ARTISAN").
         requestMatchers("/client/**").hasAuthority("CLIENT").
         requestMatchers("/admin/**").hasAuthority("ADMIN").
         requestMatchers("/user/**").hasAnyAuthority("ARTISAN","CLIENT","ADMIN").
         anyRequest().authenticated());
       http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    }




    
