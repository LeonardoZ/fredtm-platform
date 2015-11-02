package com.fredtm.api.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredtm.api.email.ChangePasswordMail;
import com.fredtm.api.email.Mail;
import com.fredtm.api.resource.AccountResourceAssembler;
import com.fredtm.core.model.Account;
import com.fredtm.core.util.PasswordEncryptionService;
import com.fredtm.resources.AccountDTO;
import com.fredtm.resources.ChangeToken;
import com.fredtm.resources.SendAccountDTO;
import com.fredtm.resources.base.ChangePasswordDTO;
import com.fredtm.resources.security.LoginDTO;
import com.fredtm.resources.security.LoginResponse;
import com.fredtm.service.AccountService;
import com.fredtm.service.ChangePasswordService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value = "fredapi/account")
public class AccountController implements ResourcesUtil<Account, AccountDTO> {

	@Autowired
	private AccountService service;

	@Autowired
	private AccountResourceAssembler assembler;

	@Autowired
	private ChangePasswordService changePassword;

	@Autowired
	private Mail mail;

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Resource<AccountDTO>> createAccount(@RequestBody SendAccountDTO sendAccount) {

		Account createdAccount = service.createAccount(sendAccount.getEmail(), sendAccount.getPassword(),
				sendAccount.getName());

		Resource<AccountDTO> resource = configureResource(createdAccount);
		return new ResponseEntity<Resource<AccountDTO>>(resource, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{uuid}")
	public HttpEntity<Resource<AccountDTO>> getAccount(@PathVariable(value = "uuid") String id) {
		Account account = service.getAccount(id);
		Resource<AccountDTO> resource = configureResource(account);
		return new ResponseEntity<Resource<AccountDTO>>(resource, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public HttpEntity<LoginResponse> loginAccount(@RequestBody LoginDTO resource) {

		Optional<Account> logedAccount = service.loginAccount(resource.getEmail(), resource.getPassword());
		if (logedAccount.isPresent()) {
			Account account = logedAccount.get();

			Instant instant = new Date().toInstant();
			Instant validatedAt = instant.plus(Duration.ofHours(7).plusMinutes(15));
			Date date = Date.from(validatedAt);
			LoginResponse response = new LoginResponse(Jwts.builder().setSubject(account.getEmail())
					.claim("roles", account.getRoles()).setIssuedAt(new Date()).setExpiration(date)
					.signWith(SignatureAlgorithm.HS256, PasswordEncryptionService.RANDOM_KEY).compact());
			response.setAccountUuid(account.getUuid());
			return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping(value = "/token/espc", method = RequestMethod.POST)
	public HttpEntity<ChangeToken> getChangePasswordToken(@RequestBody AccountDTO accountDTO) {
		Account account = service.getAccount(accountDTO.getUuid());
		if (account == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		ChangeToken token = changePassword.createToken(account);
		ChangePasswordMail mailModel = new ChangePasswordMail();
		mail.sendMail(token.getEmail(), mailModel.getTitle(), mailModel.getContent(token.getJwt()));
		return new HttpEntity<ChangeToken>(token);
	}

	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public HttpStatus sendChangePasswordTokentoEmail(@RequestBody AccountDTO accountDTO) {
		Account account = service.getAccount(accountDTO.getUuid());
		if (account == null) {
			return HttpStatus.UNAUTHORIZED;
		}
		ChangeToken token = changePassword.createToken(account);
		ChangePasswordMail mailModel = new ChangePasswordMail();
		mail.sendMail(token.getEmail(), mailModel.getTitle(), mailModel.getContent(token.getJwt()));
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public ResponseEntity<Resource<AccountDTO>> changePassword(@RequestBody ChangePasswordDTO dto) {

		Optional<Account> validAccount = changePassword.isValidToken(dto.getToken());
		if (!validAccount.isPresent()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		Account changed = service.changePassword(validAccount.get(), dto.getNewPassword());
		if (changed != null) {
			Resource<AccountDTO> resource = configureResource(changed);
			return new ResponseEntity<Resource<AccountDTO>>(resource, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	public Resource<AccountDTO> configureResource(Account account) {
		// self

		AccountDTO resource = assembler.toResource(account);
		Link create = linkTo(methodOn(AccountController.class).createAccount(null)).withRel("createAccount");
		Link getAccount = linkTo(methodOn(AccountController.class).getAccount(resource.getUuid()))
				.withRel("getAccount");
		Link self = linkTo(AccountController.class).slash(account.getId()).withSelfRel();
		return new Resource<AccountDTO>(resource, self, create, getAccount);
	}

}
