/*
 * Created by Sachin
 */
package com.ecomm.shopping.eShop.config;

import com.ecomm.shopping.eShop.service.UserInfoProviderService;
import com.ecomm.shopping.eShop.worker.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//to enable method level roles
@EnableMethodSecurity
public class SecurityConfig {

    //CONFIGURING USERS
    @Bean
    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.withUsername("aa")
//                .password(passwordEncoder().encode("Mpik|@27c("))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withUsername("sachin")
//                .password(passwordEncoder().encode("88888888"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user);

        return new UserInfoProviderService();
    }

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    //CONFIGURING ACCESS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/app/*","/*.css","*.scss")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/app/**")
                .authenticated().and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403") // Custom forbidden error page
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .and()
                .build();
    }


    //password encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        //giving info about who is the user details service and password encoder
        //these info can be used to generate user details object and set it to authentication object.
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception{
        return (web) -> web.ignoring().antMatchers("/static/**","/templates/**");
    }


}
