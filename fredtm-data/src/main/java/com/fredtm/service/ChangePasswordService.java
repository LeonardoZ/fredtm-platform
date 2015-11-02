package com.fredtm.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.model.Account;
import com.fredtm.core.util.PasswordEncryptionService;
import com.fredtm.resources.ChangeToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class ChangePasswordService {

	@Autowired
	private AccountService service;

	public Optional<Account> isValidToken(String token) {
		ChangeToken change = new ChangeToken();
		change.setJwt(token);
		return isValidToken(change);
	}

	public Optional<Account> isValidToken(ChangeToken token) {

		final Claims claims = Jwts.parser().setSigningKey(PasswordEncryptionService.RANDOM_KEY)
				.parseClaimsJws(token.getJwt()).getBody();

		String uuid = (String) claims.get("uuid");
		Account account = service.getAccount(uuid);

		return Optional.ofNullable(account);
	}

	public ChangeToken createToken(Account account) {
		Instant instant = new Date().toInstant();
		Instant validatedAt = instant.plus(Duration.ofHours(1));
		Date date = Date.from(validatedAt);

		String jwt = Jwts.builder().setSubject(account.getEmail()).claim("uuid", account.getUuid())
				.setIssuedAt(new Date()).setExpiration(date)
				.signWith(SignatureAlgorithm.HS256, PasswordEncryptionService.RANDOM_KEY).compact();
		return new ChangeToken(account.getUuid(), account.getEmail(), jwt);
	}

}
