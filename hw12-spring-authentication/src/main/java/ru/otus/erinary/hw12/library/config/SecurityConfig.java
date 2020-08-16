package ru.otus.erinary.hw12.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/img/**")
                .antMatchers("/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/library/login/**").permitAll()
                .antMatchers("/auth_check").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/library/login")
                .loginProcessingUrl("/auth_check")
                .defaultSuccessUrl("/library")
                .failureUrl("/library/login/failed")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/library/login");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //TODO нормальный encoder
        return NoOpPasswordEncoder.getInstance();
    }
}
