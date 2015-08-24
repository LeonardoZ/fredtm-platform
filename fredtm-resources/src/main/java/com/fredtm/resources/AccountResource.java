package com.fredtm.resources;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fredtm.resources.base.FredResourceSupport;
import com.fredtm.resources.base.SimpleLink;
import com.google.gson.annotations.SerializedName;

@Relation(value = "account", collectionRelation = "accounts")
public class AccountResource extends FredResourceSupport {

	private String name;
	private String email;
	private String password;

	@JsonIgnore
	@SerializedName("_links")
	private Links links;

	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
	}

	public class Links {
		SimpleLink self;
		SimpleLink loginAccount;
		SimpleLink createAccount;
		SimpleLink getAccount;

		public Links() {
		}

		public SimpleLink getSelf() {
			return self;
		}

		public void setSelf(SimpleLink self) {
			this.self = self;
		}

		public SimpleLink getLoginAccount() {
			return loginAccount;
		}

		public void setLoginAccount(SimpleLink loginAccount) {
			this.loginAccount = loginAccount;
		}

		public SimpleLink getCreateAccount() {
			return createAccount;
		}

		public void setCreateAccount(SimpleLink createAccount) {
			this.createAccount = createAccount;
		}

		public SimpleLink getGetAccount() {
			return getAccount;
		}

		public void setGetAccount(SimpleLink getAccount) {
			this.getAccount = getAccount;
		}

	}

	public AccountResource uuid(String uuid) {
		setUuid(uuid);
		return this;
	}

	public AccountResource name(String name) {
		this.name = name;
		return this;
	}

	public AccountResource email(String email) {
		this.email = email;
		return this;
	}

	public AccountResource password(String pass) {
		this.password = pass;
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPasswordHash(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(email).append(password)
				.append(name).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountResource other = (AccountResource) obj;
		return new EqualsBuilder().append(getEmail(), other.getEmail())
				.append(getName(), other.getName())
				.append(getPassword(), other.getPassword()).isEquals();

	}

	@Override
	public String toString() {
		return "AccountResource [name=" + name + ", email=" + email
				+ ", password=" + password + ", getUuid()=" + getUuid()
				+ ", getLinks()=" + links + "]";
	}

}