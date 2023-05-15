package by.academy.pharmacy2.config;

import by.academy.pharmacy2.service.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static by.academy.pharmacy2.entity.Constant.ADMINISTRATOR;
import static by.academy.pharmacy2.entity.Constant.ADMINISTRATOR1;
import static by.academy.pharmacy2.entity.Constant.AUTH_LOGIN;
import static by.academy.pharmacy2.entity.Constant.AUTH_LOGIN_ERROR;
import static by.academy.pharmacy2.entity.Constant.AUTH_REGISTRATION;
import static by.academy.pharmacy2.entity.Constant.CLIENT;
import static by.academy.pharmacy2.entity.Constant.CLIENT_MEDICINE;
import static by.academy.pharmacy2.entity.Constant.CLIENT_MEDICINE_PRODUCTS_ADD_TO_CART;
import static by.academy.pharmacy2.entity.Constant.CSS;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.ERROR;
import static by.academy.pharmacy2.entity.Constant.IMG;
import static by.academy.pharmacy2.entity.Constant.JS;
import static by.academy.pharmacy2.entity.Constant.LOGOUT;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST1;
import static by.academy.pharmacy2.entity.Constant.PROCESS_LOGIN;
import static by.academy.pharmacy2.entity.Constant.SLASH;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsServiceImpl;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(ADMINISTRATOR1)
                .hasRole(ADMINISTRATOR)
                .requestMatchers(PHARMACIST1)
                .hasRole(PHARMACIST)
                .requestMatchers(CLIENT_MEDICINE_PRODUCTS_ADD_TO_CART)
                .hasAnyRole(CLIENT, PHARMACIST, ADMINISTRATOR)
                .requestMatchers(JS, CSS, IMG, AUTH_LOGIN, AUTH_REGISTRATION, SLASH, EMPTY,
                        CLIENT_MEDICINE, ERROR)
                .permitAll()
                .anyRequest()
                .hasAnyRole(CLIENT, PHARMACIST, ADMINISTRATOR).and()
                .formLogin()
                .loginPage(AUTH_LOGIN)
                .loginProcessingUrl(PROCESS_LOGIN)
                .defaultSuccessUrl(SLASH, true)
                .failureUrl(AUTH_LOGIN_ERROR)
                .and()
                .logout()
                .logoutUrl(LOGOUT)
                .logoutSuccessUrl(SLASH);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new PasswordUtil();
    }
}
