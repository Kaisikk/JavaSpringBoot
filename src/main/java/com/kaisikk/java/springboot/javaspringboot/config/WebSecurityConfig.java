package com.kaisikk.java.springboot.javaspringboot.config;

import com.kaisikk.java.springboot.javaspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * @author Kaisikk
 *
 * Конфиг секьюрити
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Autowired
    private UserService userService;


    /**
     * Фильтр для секьюрити
     *
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                // разрешаем заходить по этому адресу всем, остальные страницы - только если залогинился
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/registration").permitAll()
                        .anyRequest().authenticated()
                )
                // указываем где лежит форма логина
                .formLogin((form) -> form
                        .loginPage("/login")
                        // разрешаем всем доступ к логину
                        .permitAll()
                )

                // разрешаем всем разлогиниваться
                .logout((logout) -> logout.permitAll());

        return http.build();
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userService)
                        .passwordEncoder(new BCryptPasswordEncoder());
//

    }
}