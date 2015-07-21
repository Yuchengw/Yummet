package com.yummet.web.security;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * @author yucheng
 * @since 1 This Filter is overriding spring security default CSRF filter to
 *        adapt to Angular Client side
 * */
class StatelessXSRFFilter extends OncePerRequestFilter {

	private final String RETURN_PATH = "/";
	private static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";
	public RequestMatcher requireCsrfProtectionMatcher = new DefaultRequiresCsrfMatcher();
	private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
	
	/**
	 * The default {@link RequestMatcher} that indicates if CSRF protection is required or
	 * not. The default is to ignore GET, HEAD, TRACE, OPTIONS and process all other
	 * requests.
	 */

	private final Log logger = LogFactory.getLog(getClass());

	public StatelessXSRFFilter() {
		//
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String csrf = request.getHeader(X_CSRF_TOKEN);
		if (csrf != null && csrf.length() > 0) {
			Cookie cookie = WebUtils.getCookie(request, X_CSRF_TOKEN);
			if (cookie == null || csrf != null && !csrf.equals(cookie.getValue())) {
				cookie = new Cookie(X_CSRF_TOKEN, csrf);
				cookie.setPath(RETURN_PATH);
				response.addCookie(cookie);
			}
		} 
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
