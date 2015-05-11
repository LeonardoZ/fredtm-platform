package com.fredtm.api.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.hateoas.ResourceSupport;

public class AccountDto extends ResourceSupport {

	private int pkId;
	private String name;
	private String email;
	private String password;

	public AccountDto id(int id) {
		this.pkId = id;
		return this;
	}

	public AccountDto name(String name) {
		this.name = name;
		return this;
	}

	public AccountDto email(String email) {
		this.email = email;
		return this;
	}

	public AccountDto password(String pass) {
		this.password = pass;
		return this;
	}

	public int getPkId() {
		return pkId;
	}

	public void setPkId(int id) {
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
		AccountDto other = (AccountDto) obj;
		return new EqualsBuilder().append(getEmail(), other.getEmail())
				.append(getName(), other.getName())
				.append(getPassword(), other.getPassword()).isEquals();

	}

}
