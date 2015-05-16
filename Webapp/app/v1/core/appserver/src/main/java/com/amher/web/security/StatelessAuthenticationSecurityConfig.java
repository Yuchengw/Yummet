package com.amher.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.amher.lib.objectProvider.UserDetailsProvider;
import com.amher.lib.objectProvider.UserDetailsProviderImpl;
import com.amher.service.authentication.TokenAuthenticationService;
import com.amher.service.authentication.UserDetailsServiceImpl;

/**
 * @author yucheng
 * @version 1
 * */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity//( prePostEnabled = true )
@Order(3)
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * This is the java hardcode version
	 * */
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	public StatelessAuthenticationSecurityConfig() {
		super(true);
	}
	
	@Bean
	public UserDetailsProvider userDetailsProvider() {
		return new UserDetailsProviderImpl();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
				.anonymous().and()
				.servletApi().and()
				.authorizeRequests()				
				.antMatchers("/vw/**").permitAll()
				.antMatchers("/favicon.ico").permitAll()
				.antMatchers("/rs/**").permitAll()
				// allow anonymous POSTs to login
				.antMatchers(HttpMethod.POST, "/service/login").permitAll()
				// allow anonymous GETs to API
				.antMatchers(HttpMethod.GET, "/service/**").hasRole("USER")
				// defined Admin only API area
				.antMatchers("/service/admin/**").hasRole("ADMIN")
				// all other request need to be authenticated
				.anyRequest().hasRole("USER")
				.and()
				// custom JSON based authentication by POST of
				// {"username":"<name>","password":"<password>"} which sets the
				// token header upon authentication
				.addFilterBefore(
						new StatelessLoginFilter("/service/login", tokenAuthenticationService, userDetailsService,
							authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				// custom Token based authentication based on the header
				// previously given to the client
				.addFilterBefore(
						new StatelessAuthenticationFilter(tokenAuthenticationService),
						UsernamePasswordAuthenticationFilter.class);
	}

	@Bean(name="myAuthenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
