package com.yummet.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yummet.lib.objectProvider.UserProvider;

/**
 * @author yucheng
 * @since 1
 * */
@Configuration
@EnableWebSecurity
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private com.yummet.web.security.UserDetailsService userDetailsService;
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;
	
	public StatelessAuthenticationSecurityConfig() {
		super(true);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		.ignoring()
		.antMatchers("/as/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		HttpSecurity t = http
		.exceptionHandling().and()
		.anonymous().and()
		.servletApi().and()
		.headers().and()
		.authorizeRequests()
						
		//allow anonymous resource requests
		.antMatchers("/").permitAll() // home page
		.antMatchers("/rs/**").permitAll() // javascript code
		
		//allow anonymous POSTs to login
		.antMatchers(HttpMethod.POST, "/service/login").permitAll()
		
		//defined Admin only API area
		.antMatchers("/admin/**").hasRole("ADMIN")
		
		//all other request need to be authenticated
		.anyRequest().hasRole("USER").and();				

		// custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
		t.addFilterBefore(new StatelessLoginFilter("/service/login", tokenAuthenticationService, userDetailsService, authenticationManager()), UsernamePasswordAuthenticationFilter.class);

		// custom Token based authentication based on the header previously given to the client
		t.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}

}
