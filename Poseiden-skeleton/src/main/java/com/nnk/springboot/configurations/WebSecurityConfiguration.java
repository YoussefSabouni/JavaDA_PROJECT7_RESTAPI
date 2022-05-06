package com.nnk.springboot.configurations;

import com.nnk.springboot.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class extends the {@link WebSecurityConfigurerAdapter} interface to customise the security rules automatically
 * defined by Spring Security with the {@link EnableWebSecurity} annotation.
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    /**
     * Create a new instance of {@link WebSecurityConfiguration}. This will be done automatically by SpringBoot with
     * dependency injection.
     *
     * @param userService
     *         instance of {@link UserService}.
     */
    public WebSecurityConfiguration(UserService userService) {

        this.userService = userService;
    }

    /**
     * Any endpoint that requires defense against common vulnerabilities can be specified here, including public ones.
     *
     * @param http
     *         the {@link HttpSecurity} to modify
     *
     * @throws Exception
     *         if an error occurs.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
            .antMatchers("/static/**", "/h2-console/**")
            .permitAll()
            .antMatchers("/login*")
            .anonymous()
            .antMatchers("/admin*")
            .hasRole("ADMIN")
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/bidList/list")
            .failureUrl("/login?error")
            .and()
            .oauth2Login()
            .loginPage("/app-login")
            .defaultSuccessUrl("/bidList/list")
            .and()
            .logout()
            .logoutUrl("/app-logout")
            .logoutSuccessUrl("/login?logout");
    }

    /**
     * Allows you to specify the {@link AuthenticationManager}.
     *
     * @param authManagerBuilder
     *         allows for easily building in memory authentication
     *
     * @throws Exception
     *         if an error occurs.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {

        authManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * Exposes the {@link AuthenticationManager} as a Bean.
     *
     * @return the {@link AuthenticationManager}.
     *
     * @throws Exception
     *         if an error occurs.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    /**
     * Exposes the
     *
     * @return the new {@link BCryptPasswordEncoder} instance.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
