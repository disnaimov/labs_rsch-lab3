package com.psuti.kiselev.config;


import com.psuti.kiselev.security.SuccessLogoutHandlerImpl;
import com.psuti.kiselev.service.ConfirmFilter;
import com.psuti.kiselev.service.JwtAuthenticationEntryPoint;
import com.psuti.kiselev.service.JwtRequestFilter;
import com.psuti.kiselev.service.UserVerificationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final UserVerificationFilter userVerificationFilter;
    private final ConfirmFilter confirmFilter;
    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService,
                             JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             JwtRequestFilter jwtRequestFilter,
                             UserVerificationFilter userVerificationFilter, ConfirmFilter confirmFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
        this.userVerificationFilter = userVerificationFilter;
        this.confirmFilter = confirmFilter;
    }
    @Bean
    SecurityFilterChain web(HttpSecurity http,
                            SuccessLogoutHandlerImpl successLogoutHandler) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .antMatchers(
                                        "/auth/login",
                                        "/auth/reg/",
                                        "/auth/reg/mail")
                                .permitAll()
                                .antMatchers("/").authenticated()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/auth/logout")
                                .deleteCookies("JSESSION")
                                .invalidateHttpSession(true)
                                .logoutSuccessHandler(successLogoutHandler)
                )
                .userDetailsService(userDetailsService)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(userVerificationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(confirmFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}


