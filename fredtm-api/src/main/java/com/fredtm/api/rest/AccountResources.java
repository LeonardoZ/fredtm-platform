package com.fredtm.api.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import com.fredtm.api.dto.AccountResource;
import com.fredtm.api.dto.AccountResourceAssembler;
import com.fredtm.core.model.Account;
import com.fredtm.service.AccountService;

@Component
@Path(value = "/account")
@Produces(value = MediaType.APPLICATION_JSON)
@ExposesResourceFor(AccountResource.class)
public class AccountResources {

	@Autowired
	private EntityLinks entityLinks;

	@Autowired
	private AccountService service;

	@POST
	public Response createAccount(AccountResource accountresource) {
		Account createdAccount = service.createAccount(
				accountresource.getEmail(), accountresource.getPassword(),
				accountresource.getName());

		AccountResource resource = configureResource(createdAccount);
		Link create = entityLinks.linkFor(AccountResource.class)
				.withRel("self");
		resource.add(create);
		return Response.ok(resource).build();
	}

	@GET
	@Path("/{id}")
	public Response getAccount(@PathParam(value = "id") long id) {
		Account account = service.getAccount(id);
		AccountResource resource = configureResource(account);

		Link getAccount = entityLinks.linkFor(AccountResource.class)
				.slash(resource.getPkId()).withRel("self");
		resource.add(getAccount);
		return Response.ok().build();
	}

	@POST
	@Path("/login")
	public Response loginAccount(AccountResource resource) {
		Optional<Account> logedAccount = service.loginAccount(
				resource.getEmail(), resource.getPassword());
		if (logedAccount.isPresent()) {
			Account account = logedAccount.get();
			AccountResource accountResource = configureResource(account);
			Link self = entityLinks.linkFor(AccountResource.class)
					.slash("/login").withSelfRel();
			accountResource.add(self);
			return Response.ok(accountResource).build();
		} else {
			return Response.status(401).build();
		}
	}

	@GET
	@Path("/all")
	public Response getAllAccounts(
			@QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("elements") @DefaultValue("3") int elements) {
		Page<Account> allAccounts = service.getAllAccounts(page, elements);
		if (!allAccounts.hasContent()) {
			return Response.ok().build();
		}
		List<AccountResource> ress = configureResources(allAccounts
				.getContent());

		PagedResources<AccountResource> resources = new PagedResources<>(ress,
				new PagedResources.PageMetadata(3, allAccounts.getNumber(),
						allAccounts.getNumberOfElements(),
						allAccounts.getTotalPages()));
		resources.add(JaxRsLinkBuilder.linkTo(AccountResources.class)
				.slash("/all").withRel("self"));
		List<Link> links = new ArrayList<>();
		Pageable prev, next;
		// Algumas páginas
		// prev next first last
		if (allAccounts.hasNext()) {
			next = allAccounts.nextPageable();
			links.add(pageLink(next.getPageNumber(), elements, "next"));
		}
		if (allAccounts.hasPrevious()) {
			prev = allAccounts.previousPageable().previousOrFirst();
			links.add(pageLink(prev.getPageNumber(), elements, "prev"));
		}

		links.add(pageLink(0, elements, "first"));
		links.add(pageLink(allAccounts.getTotalPages(), elements, "last"));

		resources.add(links);

		return Response.ok(resources).build();
	}

	public Link pageLink(int number, int elements, String rel) {
		Link otherPage = entityLinks.linkFor(AccountResource.class)
				.slash("/all?page=" + number + "&elements=" + elements)
				.withRel(rel);
		return otherPage;
	}

	private List<AccountResource> configureResources(List<Account> accounts) {
		List<AccountResource> ress = new ArrayList<>();
		accounts.iterator().forEachRemaining(
				a -> ress.add(configureResource(a)));
		return ress;
	}

	private AccountResource configureResource(Account account) {
		// self
		AccountResourceAssembler assembler = new AccountResourceAssembler();
		AccountResource resource = assembler.toResource(account);
		Link create = entityLinks.linkFor(AccountResource.class).withRel(
				"createAccount");
		Link login = entityLinks.linkFor(AccountResource.class).slash("/login")
				.withRel("loginAccount");
		Link getAccount = entityLinks.linkFor(AccountResource.class)
				.slash(resource.getPkId()).withRel("getAccount");
		resource.add(login);
		resource.add(create);
		resource.add(getAccount);
		return resource;
	}

}
