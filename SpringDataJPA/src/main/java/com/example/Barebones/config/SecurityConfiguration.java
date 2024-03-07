package com.example.Barebones.config;


import com.example.Barebones.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration  {
// We will create userService class in upcoming step
   @Autowired
   private UserServiceImpl userService;

   /*@Override
   protected void configure(HttpSecurity http) throws Exception {
       http
               .authorizeRequests()
                   .antMatchers(
                           "/registration**",
                           "/js/**",
                           "/css/**",
                           "/img/**",
                           "/webjars/**").permitAll()
                   .anyRequest().authenticated()
               .and()
                   .formLogin()
                       .loginPage("/login")
                           .permitAll()
               .and()
                   .logout()
                       .invalidateHttpSession(true)
                       .clearAuthentication(true)
                       .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                       .logoutSuccessUrl("/login?logout")
               .permitAll()
               // Handle logout
               .and()
               .logout().invalidateHttpSession(true).clearAuthentication(true)
               .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
               .logoutSuccessUrl("/logoutSuccess").permitAll();
   }
*/
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       System.out.println("===========>IN filterChain() ");
       http.authorizeHttpRequests().requestMatchers(
                       "/registration**",
                       "/js/**",
                       "/css/**",
                       "/img/**",
                       "/webjars/**").permitAll().anyRequest().authenticated().and().formLogin()
               .loginPage("/login").permitAll().and().logout()
               .invalidateHttpSession(true)
               .clearAuthentication(true)
               .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
               .logoutSuccessUrl("/login?logout").permitAll();
       return http.build();
   }
   //You may declare @Bean methods as static, allowing for them to be called without 
   //creating their containing configuration class as an instance
   // this eliminates having to do this in app prop file:
   // spring.main.allow-circular-references: true
   @Bean
   public static BCryptPasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }

   @Bean
   public DaoAuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
       auth.setUserDetailsService(userService);
       auth.setPasswordEncoder(passwordEncoder());
       return auth;
   }


   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.authenticationProvider(authenticationProvider());
   }


}