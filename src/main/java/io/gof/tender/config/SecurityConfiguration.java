package io.gof.tender.config;

import io.gof.tender.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /*auth
            .inMemoryAuthentication()
            .withUser("pronasadmin").password("Gof@123").roles("ADMIN");*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
            .authenticationProvider(new AuthenticationProvider() {
                @Override
                public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                    String name = authentication.getName();
                    String password = authentication.getCredentials().toString();

                    //TODO should be authenticated through DB
                    List<GrantedAuthority> grantedAuths = new ArrayList<>();
                    grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            User.builder()
                                .username("admin")
                                .name("Admin")
                            , password, grantedAuths);

                    return auth;
                }

                @Override
                public boolean supports(Class<?> aClass) {
                    return aClass.equals(UsernamePasswordAuthenticationToken.class);
                }
            })
            .authorizeRequests()
                .antMatchers(
                        "*comment/add*",
                        "*post/add*",
                        "*vote/add*",
                        "*thumb/up*",
                        "*thumb/down*"
                ).authenticated()
                .antMatchers("/processlogin").permitAll()
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginProcessingUrl("/processlogin")
                .successHandler((request, response, authentication) -> {
                    response.sendRedirect("/api/users/get/logged-in");
                })
                .failureHandler((request, response, e) -> {
                    response.getWriter().write("Login Failed");
                    response.setStatus(403);
                })
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

}
