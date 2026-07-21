package com.ecommerce.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder getEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request ->
                                request.requestMatchers("/test/**").permitAll()
                                .requestMatchers("/admin/register")
                                .permitAll()
                                .requestMatchers("/admin/**").authenticated()
                                .requestMatchers("/users/signup").permitAll()
                                .requestMatchers("/users/getAllUser").hasRole("ADMIN")
                                .requestMatchers("/users/update-phone/**").permitAll()
                                .requestMatchers("/users/**").permitAll()
                                .requestMatchers("/address/save-address").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/address/**").permitAll()
                                .requestMatchers("/products/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(
                        form ->
                                form.loginPage("/login")
                                        .defaultSuccessUrl("/dashboard", true)
                                        .permitAll()
                )
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }


}
