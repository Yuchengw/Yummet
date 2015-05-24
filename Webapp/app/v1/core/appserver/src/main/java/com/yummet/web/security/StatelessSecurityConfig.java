package com.yummet.web.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

/**
 * Since we are stateless-driven webapplication, we need more flexibility in Security Side
 * @author yucheng
 * @since 1
 * */
@Deprecated
@EnableWebSecurity
public class StatelessSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().addFilterBefore(new StatelessCSRFFilter(), CsrfFilter.class);
	}
}
