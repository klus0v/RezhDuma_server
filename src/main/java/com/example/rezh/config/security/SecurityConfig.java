package com.example.rezh.config.security;


import com.example.rezh.filter.CustomAuthenticationFilter;
import com.example.rezh.filter.CustomAuthorizationFilter;
import com.example.rezh.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), userService);
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**", "/api/registration/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/news/**", "/api/projects/**", "/api/history/**", "/api/documents/**", "/api/appeals/popular/**", "/api/search/**",  "/api/votes/**").permitAll();

        http.authorizeRequests().antMatchers("/api/users/set/**").hasAuthority("SUPER_ADMIN");

        http.authorizeRequests().antMatchers(GET, "/api/appeals/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(POST, "/api/appeals/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(PATCH, "/api/appeals/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(DELETE, "/api/appeals/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(PATCH, "/api/votes/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(GET, "/api/users/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(PUT, "/api/users/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(PATCH, "/api/users/**").hasAuthority("USER");


        http.authorizeRequests().antMatchers(DELETE, "/api/news/**", "/api/projects/**", "/api/history/**", "/api/documents/**", "/api/votes/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/news/**", "/api/projects/**", "/api/history/**", "/api/documents/**", "/api/votes/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(PATCH, "/api/news/**", "/api/projects/**", "/api/history/**", "/api/documents/**", "/api/appeals/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/appeals/admin/**").hasAuthority("ADMIN");




        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
