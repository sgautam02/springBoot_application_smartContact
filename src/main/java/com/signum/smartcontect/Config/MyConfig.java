// package com.signum.smartcontect.Config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// @Configuration
// @EnableWebSecurity
// public class MyConfig  {

//     @Bean
//     public UserDetailsService getUserDetailsService(){
//         return new UserDetailsServiceImpli();
//     }

//     @Bean
//     public BCryptPasswordEncoder passwordEncoder(){
//         return new BCryptPasswordEncoder();
//     }
//     @Bean
//     public DaoAuthenticationProvider authenticationProvider(){
//         DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
//         daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
//         daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//         return null;
//     }
   

   
//     protected void configure(AuthenticationManagerBuilder auth )throws Exception {
//         auth.authenticationProvider(authenticationProvider());
//     }
    
   

//     protected void configure(HttpSecurity http) throws Exception {
    
//         http.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("User").requestMatchers("/**").permitAll().and().formLogin().and().csrf().disable();
            
//     }

// }
