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

import com.fredtm.api.resource.AccountResourceAssembler;
import com.fredtm.core.model.Account;
import com.fredtm.core.model.PasswordEncryptionService;
import com.fredtm.resources.AccountDTO;
import com.fredtm.resources.SendAccountDTO;
import com.fredtm.resources.security.LoginDTO;
import com.fredtm.resources.security.LoginResponse;
import com.fredtm.service.AccountService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value = "fredapi/account")
public class AccountController implements ResourcesUtil<Account, AccountDTO> {

	@Autowired
	private AccountService service;

	@Autowired
	private AccountResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Resource<AccountDTO>> createAccount(@RequestBody SendAccountDTO sendAccount) {

		Account createdAccount = service.createAccount(
				sendAccount.getEmail(),
				sendAccount.getPassword(),
				sendAccount.getName()
		);

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
			LoginResponse response = new LoginResponse(
					 Jwts.builder().setSubject(account.getEmail())
					.claim("roles", account.getRoles())
					.setIssuedAt(new Date())
					.setExpiration(date)
					.signWith(SignatureAlgorithm.HS256, PasswordEncryptionService.RANDOM_KEY)
					.compact());
			 response.setAccountUuid(account.getUuid());
			return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	// @RequestMapping(method = RequestMethod.GET, value = "/all")
	// public HttpEntity<PagedResources<Resource<AccountResource>>>
	// getAllAccounts(
	// @RequestParam(value = "page", defaultValue = "0") int page,
	// @RequestParam(value = "elements", defaultValue = "3") int elements) {
	//
	// Page<Account> allAccounts = service.getAllAccounts(page, elements);
	//
	// if (!allAccounts.hasContent()) {
	// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	// }
	// List<Account> content = allAccounts.getContent();
	// List<Resource<AccountResource>> ress = configureResources(content);
	//
	// PagedResources<Resource<AccountResource>> resources = new
	// PagedResources<>(
	// ress,
	// new PagedResources.PageMetadata(3, allAccounts.getNumber(),
	// allAccounts.getNumberOfElements(),
	// allAccounts.getTotalPages()));
	// List<Link> links = new ArrayList<>();
	// Pageable prev, next;
	// // Algumas páginas
	// // prev next first last
	// if (allAccounts.hasNext()) {
	// next = allAccounts.nextPageable();
	// links.add(pageLink(next.getPageNumber(), elements, "next"));
	// }
	// if (allAccounts.hasPrevious()) {
	// prev = allAccounts.previousPageable().previousOrFirst();
	// links.add(pageLink(prev.getPageNumber(), elements, "prev"));
	// }
	//
	// links.add(pageLink(0, elements, "first"));
	// links.add(pageLink(allAccounts.getTotalPages(), elements, "last"));
	//
	// resources.add(links);
	// return new
	// ResponseEntity<PagedResources<Resource<AccountResource>>>(resources,HttpStatus.OK);
	// }
	//
	// public Link pageLink(int number, int elements, String rel) {
	// Link otherPage = linkTo(
	// methodOn(AccountController.class).getAllAccounts(number,
	// elements)).withRel(rel);
	// return otherPage;
	//
	// }
	//
	// public AccountResource configureResource(Account account) {
	// // self
	// AccountResource resource = assembler.toResource(account);
	// Link create = linkTo(
	// methodOn(AccountController.class).createAccount(resource))
	// .withRel("createAccount");
	// Link login = linkTo(
	// methodOn(AccountController.class).loginAccount(resource))
	// .withRel("loginAccount");
	// Link getAccount = linkTo(
	// methodOn(AccountController.class).getAccount(resource.getUuid()))
	// .withRel("getAccount");
	// Link self = linkTo(AccountController.class).slash(account.getId())
	// .withSelfRel();
	// resource.add(self);
	// resource.add(login);
	// resource.add(create);
	// resource.add(getAccount);
	// return resource;
	// }

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
