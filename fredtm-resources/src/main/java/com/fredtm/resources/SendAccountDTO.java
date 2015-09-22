package com.fredtm.resources;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fredtm.resources.base.FredDTOSupport;
import com.fredtm.resources.base.SimpleLink;
import com.google.gson.annotations.SerializedName;

public class SendAccountDTO extends FredDTOSupport {

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

	public SendAccountDTO uuid(String uuid) {
		setUuid(uuid);
		return this;
	}

	public SendAccountDTO name(String name) {
		this.name = name;
		return this;
	}

	public SendAccountDTO email(String email) {
		this.email = email;
		return this;
	}

	public SendAccountDTO password(String password) {
		this.password = password;
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
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(email)
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
		SendAccountDTO other = (SendAccountDTO) obj;
		return new EqualsBuilder().append(getEmail(), other.getEmail())
				.append(getName(), other.getName()).isEquals();

	}

	@Override
	public String toString() {
		return "AccountResource [name=" + name + ", email=" + email
				+", getUuid()=" + getUuid()
				+ ", getLinks()=" + links + "]";
	}

}
