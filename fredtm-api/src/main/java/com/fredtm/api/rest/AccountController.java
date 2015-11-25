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
import com.fredtm.resources.ChangePasswordDTO;
import com.fredtm.resources.ChangeToken;
import com.fredtm.resources.SendAccountDTO;
import com.fredtm.resources.security.LoginDTO;
import com.fredtm.resources.security.LoginResponse;
import com.fredtm.service.AccountService;
import com.fredtm.service.ChangePasswordService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(consumes = "application/json", produces = "application/json", value = "Accounts", description = "Account Services")
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

	@ApiOperation(value = "Create new Account")
	@ApiResponses({ @ApiResponse(code = 200, message = "Token valid for 7 hours", response = LoginResponse.class),
			@ApiResponse(code = 406, message = "Conflict while saving Account", response = AccountDTO.class),
			@ApiResponse(code = 401, message = "Account not Authenticated") })
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Resource<AccountDTO>> createAccount(@RequestBody SendAccountDTO sendAccount) {

		Account createdAccount = service.createAccount(sendAccount.getEmail(), sendAccount.getPassword(),
				sendAccount.getName());

		Resource<AccountDTO> resource = configureResource(createdAccount);
		return new ResponseEntity<Resource<AccountDTO>>(resource, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Retrieve Account informations")
	@ApiResponse(code = 200, message = "The account that belongs to this UUID", response = LoginResponse.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{uuid}")
	public HttpEntity<Resource<AccountDTO>> getAccount(
			@ApiParam(name = "uuid", value= "The UUID from account to be viewed", required = true) 
			@PathVariable(value = "uuid") String uuid) {
		Account account = service.getAccount(uuid);
		Resource<AccountDTO> resource = configureResource(account);
		return new ResponseEntity<Resource<AccountDTO>>(resource, HttpStatus.OK);
	}

	@ApiOperation(notes = "The user must be properly authenticated to use all the other services from this API.", 
			value = "Check if the user can be authenticated")
	@ApiResponses({ @ApiResponse(code = 200, message = "Token valid for 7 hours", response = LoginResponse.class),
			@ApiResponse(code = 401, message = "Account not Authenticated") })

	
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public HttpEntity<LoginResponse> loginAccount(
			@ApiParam(name = "Login DTO", value = "The DTO containing login informations", required = true) @RequestBody LoginDTO resource) {

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

	// public ResponseEntity<ChangeToken> getChangePasswordToken(@RequestBody
	// AccountDTO accountDTO) {
	// Account account = service.getAccount(accountDTO.getUuid());
	// if (account == null) {
	// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	// }
	// ChangeToken token = changePassword.createToken(account);
	// ChangePasswordMail mailModel = new ChangePasswordMail();
	// boolean sendMail = mail.sendMail(token.getEmail(), mailModel.getTitle(),
	// mailModel.getContent(token.getJwt()));
	// if (sendMail) {
	// return new ResponseEntity<ChangeToken>(token);
	// } else {
	// return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	//
	// }
	// }

	@ApiOperation(value = "Retrieve token for password change")
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<Integer> sendChangePasswordTokentoEmail(
			@ApiParam(name = "Account DTO", value = "The Accounts DTO ", required = true) @RequestBody AccountDTO accountDTO) {
		Account account = service.getAccount(accountDTO.getUuid());
		if (account == null) {
			return new ResponseEntity<Integer>(HttpStatus.UNAUTHORIZED);
		}
		ChangeToken token = changePassword.createToken(account);
		ChangePasswordMail mailModel = new ChangePasswordMail();
		boolean sendMail = mail.sendMail(token.getEmail(), mailModel.getTitle(), mailModel.getContent(token.getJwt()));
		if (sendMail) {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Update accounts password")
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public ResponseEntity<Resource<AccountDTO>> changePassword(
			@ApiParam(name = "ChangePassword DTO", value = "The DTO of the account to be updated", required = true) @RequestBody ChangePasswordDTO dto) {

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
