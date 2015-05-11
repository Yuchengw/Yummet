package com.amher.service.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.amher.web.security.UserAuthentication;

/**
 * This is the service class that control token authentication
 * @author yucheng
 * @version 1
 * */
@Service
public class TokenAuthenticationService {
	
	private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
	private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10; // we hard code here, we should make it configurable

	private final TokenHandler tokenHandler;

	@Autowired
	public TokenAuthenticationService(@Value("${token.secret}") String secret) {
		tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
	}

	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		final UserDetailsImpl userDetailsImpl = authentication.getDetails();
		userDetailsImpl.setExpires(System.currentTimeMillis() + TEN_DAYS);
		response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(userDetailsImpl));
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			final UserDetailsImpl userDetailsImpl = tokenHandler.parseUserFromToken(token);
			if (userDetailsImpl != null) {
				return new UserAuthentication(userDetailsImpl);
			}
		}
		return null;
	}
}
