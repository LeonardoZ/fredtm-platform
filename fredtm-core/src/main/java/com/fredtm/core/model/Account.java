package com.fredtm.core.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fredtm.core.util.HashGenerator;

@Entity
@Table(name = "account")
public class Account extends FredEntity {

	private static final long serialVersionUID = 7384555886305860670L;

	@Column(nullable = false, length = 120, unique = true)
	private String email;

	@Column(nullable = false, length = 255, name = "password_hash")
	private String passwordHash;

	@Column(nullable = false, length = 120)
	private String name;

	@OneToMany(mappedBy = "account")
	private List<Operation> operations;

	public Account() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPassword(String pass) {
		String hash = new HashGenerator().toHash(pass);
		this.passwordHash = hash;
	}

	public void hashInfo() {
		passwordHash = new HashGenerator().toHash(passwordHash);		
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(email).append(passwordHash)
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
		Account other = (Account) obj;
		return new EqualsBuilder().append(getEmail(), other.getEmail())
				.append(getPasswordHash(), other.getPasswordHash())
				.append(getName(), other.getName()).isEquals();
	}


}
