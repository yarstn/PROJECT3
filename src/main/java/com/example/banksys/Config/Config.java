package com.example.banksys.Config;

import com.example.banksys.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Config {
    private final MyUserDetailsService userDetailsService;
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/employee/register/employee").permitAll()
                .requestMatchers("/api/v1/customer/register/customer").permitAll()
                .requestMatchers("/api/v1/employee/update").hasAuthority("EMPLOYEE")
                .requestMatchers("/api/v1/customer/update",
                        "/api/v1/customer/details",
                        "/api/v1/account/update",
                        "/api/v1/account/activate/{accountId}",
                        "/api/v1/account/withdraw/{accountId}/{amount}",
                        "/api/v1/account/deposit/{accountId}/{amount}","/api/v1/account/transfer/{fromAccountId}/{toAccountId}/{amount}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/user/get", "/api/v1/employee/get", "api/v1/user/add").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/get","/api/v1/account/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/account/add",
                        "/api/v1/account/delete/{accountId}"
                        ,"/api/v1/account/my-accountsc").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/account/add","/api/v1/account/block/{accountId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/employee/delete/{employeeId}"
                        ,"/api/v1/customer/delete/{customerId}",
                        "/api/v1/account/get").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/api/v1/user/logout").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).and().httpBasic();

        return http.build();

    }
}
