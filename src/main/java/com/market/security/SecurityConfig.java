package com.market.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService userDetails;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
	}
	
	//Encode the password used in serviceDetails
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//Authorize request from the paths specified
		http.authorizeRequests()
			.antMatchers("/","/webjars/**","/css/**","/h2-console/**","/public/**","/auth/**","/files/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
			.formLogin()
				.loginPage("/auth/login")
				.defaultSuccessUrl("/public/index",true)
				.loginProcessingUrl("/auth/login-post")
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/auth/logout")
				.logoutSuccessUrl("/public/index");
		
		//Disable CSRF and headers to use h2 console
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
	}

}
