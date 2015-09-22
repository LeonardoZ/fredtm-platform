package com.fredtm.api.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;

import com.fredtm.core.model.PasswordEncryptionService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@Order(value = 10)
public class JwtFilter extends GenericFilterBean {

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		String requestURI = request.getRequestURI();
		if (matches(requestURI)) {
			chain.doFilter(req, res);
			return;
		}

		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new ServletException("Missing or invalid Authorization header.");
		}

		final String token = authHeader.substring(7); // The part after "Bearer
														// "

		try {
			final Claims claims = Jwts.parser().setSigningKey(PasswordEncryptionService.RANDOM_KEY)
					.parseClaimsJws(token).getBody();
			request.setAttribute("claims", claims);
		} catch (final SignatureException e) {
			throw new ServletException("Invalid token.");
		}

		chain.doFilter(req, res);
	}

	private boolean matches(String path) {
		return path.equals("/") || AUTHORIZED_PATHS.stream().filter(p -> p.contains(path)).peek(System.out::println)
				.findAny().isPresent();
	}
	
//	public static void main(String[] args) {
//		byte[] generateSalt = PasswordEncryptionService.generateSalt();
//		byte[] encryptedPassword = PasswordEncryptionService.getEncryptedPassword("123456", generateSalt);
//		System.out.println(DatatypeConverter.printHexBinary(encryptedPassword));
//		System.out.println(DatatypeConverter.printHexBinary(generateSalt));
//	}

	private static final List<String> AUTHORIZED_PATHS = Arrays.asList("/fredapi/account/login", "/fredapi/base",
			"/fredapi/account", "/index.html", "/libs/", "/assets/", "/assets/", "/app/modules/fred.js",
			"/app/modules/config.js", "/app/controllers/login-controller.js");

}
