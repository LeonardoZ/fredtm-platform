package com.fredtm.api.resource;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.hateoas.ResourceSupport;

public class AccountResource extends ResourceSupport {

	private long pkId;
	private String name;
	private String email;
	private String password;

	public AccountResource id(long id) {
		this.pkId = id;
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

	public long getPkId() {
		return pkId;
	}

	public void setPkId(long id) {
		this.pkId = id;
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

}
