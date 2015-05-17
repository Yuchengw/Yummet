package com.amher.web.security;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * This is our self-defined StatelessCSRFFilter which is used for every incoming request
 * @author yucheng
 * @since 1
 * */
public class StatelessCSRFFilter extends OncePerRequestFilter {

	private static final String CSRF_TOKEN = "CSRF-TOKEN";
	private static final String XSRF_TOKEN = "XSRF-TOKEN";
	private final RequestMatcher requireCsrfProtectionMatcher = new DefaultRequiresCsrfMatcher();
	private final AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//if (requireCsrfProtectionMatcher.matches(request)) {
			CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
			if (csrf != null) {
			      Cookie cookie = WebUtils.getCookie(request, XSRF_TOKEN);
			      String token = csrf.getToken();
			      if (cookie==null || token!=null && !token.equals(cookie.getValue())) {
			        cookie = new Cookie(XSRF_TOKEN, token);
			        cookie.setPath("/"); // TODO: we need to figure out a better way to do this.
			        response.addCookie(cookie);
			      } else {
			    	  accessDeniedHandler.handle(request, response, new AccessDeniedException(
								"Missing or non-matching CSRF-token"));
					  return;
			      }
			}
		//}
		filterChain.doFilter(request, response);
	}

	public static final class DefaultRequiresCsrfMatcher implements RequestMatcher {
		private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

		@Override
		public boolean matches(HttpServletRequest request) {
			return !allowedMethods.matcher(request.getMethod()).matches();
		}
	}
	
}
