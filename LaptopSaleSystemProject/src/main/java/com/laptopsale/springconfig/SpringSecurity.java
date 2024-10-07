package com.laptopsale.springconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.laptopsale.Service.UserDetailsServiceImpl;


@Configuration
//@EnableWebSecurity
public class SpringSecurity {
		    @Autowired
		    private UserDetailsServiceImpl userDetailsService;

		    @Bean
		    public BCryptPasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		    }

		    @Bean
		    public DaoAuthenticationProvider authenticationProvider() {
		        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		        auth.setUserDetailsService(userDetailsService);
		        auth.setPasswordEncoder(passwordEncoder());
		        return auth;
		    }

		    
		    @Bean
			public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception{
				return http
						.authorizeHttpRequests(authorize -> authorize 
								
							.requestMatchers("/payment","/admin/**","/admin/users","/login","/logout","/profile","/profileUpdate","/register","/home","/purchase/**","/cart","/laptop/**","/forgotPassword/**","/resetPassword/**","/profile","/confirmPurchaseCart","/confirmPurchase/**","/removeFromCart","/purchasecart","/purchase/cart","/cart/**","/cartitem/**","/purchase/**","page/**").permitAll()
							.requestMatchers("/images/**","/css/**","/laptopImages/**").permitAll()
//							registry.requestMatchers("/admin/test/**").permitAll();

							
							.anyRequest().authenticated()
											
					 )
						

//						  .formLogin(login -> login. loginPage("/login") .defaultSuccessUrl("/home") .permitAll())
						  
//						.oauth2Login(Customizer.withDefaults()) ---Default
						.oauth2Login(oauth2login->
							oauth2login
							.loginPage("/login")
						
							.defaultSuccessUrl("/profile")
							
//							.permitAll()
//							.successHandler((request,response,authentication)->response.sendRedirect("/profile")); //After login success Go to this mapping
							.successHandler((request,response,authentication)->response.sendRedirect("/profile"))
							.permitAll()

				            
					
							
				)
//						.formLogin(Customizer.withDefaults()) // -- Default Login Page
//						  .formLogin(form -> form
//					                .loginPage("/login")  // Your custom login page
//					          
//					                .defaultSuccessUrl("/profile", true)  // Redirect after successful login
//					                .permitAll()
//					            )
						.logout(logout->logout.permitAll())
						
						
						.build();
				 		
			}
		   
}