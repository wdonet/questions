package com.nearsoft.questions.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.security.SpringSocialConfigurer;


@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REALM="NUUK_REALM";

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/img/**");
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        //TODO ldurazo/fcalderon We might need a database table to store the api authorized users and roles. jdbcAuthentication
        auth.inMemoryAuthentication().withUser("admin").password("12345").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").fullyAuthenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .apply(getSpringSocialConfigurer())
                .and().csrf().disable();
        //TODO ldurazo this isn' quite working yet, there is no google user login handled, and also messes with normal user login
        http.authorizeRequests().antMatchers("/api/**")
                .hasRole("ADMIN").and()
                .httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private SpringSocialConfigurer getSpringSocialConfigurer() throws Exception {
        SpringSocialConfigurer configurer = new SpringSocialConfigurer();
        configurer.signupUrl("/");
        configurer.postLoginUrl("/question/order/unanswered");
        return configurer;
    }

    public AuthenticationEntryPoint getBasicAuthEntryPoint() {
        return new NuukBasicAuthenticationEntryPoint();
    }
}
