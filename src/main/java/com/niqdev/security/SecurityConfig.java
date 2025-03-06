package com.niqdev.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.niqdev.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = 
				http.getSharedObject(AuthenticationManagerBuilder.class);
		
		authenticationManagerBuilder
				.userDetailsService(userService)
				.passwordEncoder(bCryptPasswordEncoder);
		
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		
		http.authenticationManager(authenticationManager)
				.csrf(csrf -> csrf
						.ignoringRequestMatchers("/api/**")
						.ignoringRequestMatchers("/h2-console/**"))
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
						.requestMatchers(PathRequest.toH2Console()).permitAll()
						.requestMatchers("/resources/**").permitAll()
						.requestMatchers("/webjars/**").permitAll()
						.requestMatchers("/css/**").permitAll()
						.requestMatchers("/js/**").permitAll()
						.requestMatchers("/img/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/login").permitAll()
						.anyRequest().authenticated())
				.formLogin(formLogin -> formLogin
						.loginPage("/login").permitAll()
						.loginProcessingUrl("/login")
						.successForwardUrl("/home")
						.defaultSuccessUrl("/home", true)
						.failureUrl("/login?error"))
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
						.logoutSuccessUrl("/login?logout").permitAll())
				.httpBasic(httpBasic -> Customizer.withDefaults());
		
		return http.build();
	}

}
