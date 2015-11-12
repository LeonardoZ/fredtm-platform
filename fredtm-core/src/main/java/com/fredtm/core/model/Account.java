package com.fredtm.core.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "account")
public class Account extends FredEntity {

	private static final long serialVersionUID = 7384555886305860670L;

	@Column(nullable = false, length = 120, unique = true)
	private String email;

	@Column(columnDefinition = "binary(20)", nullable = false, name = "password_hash")
	private byte[] passwordHash;

	@Column(columnDefinition = "binary(8)", nullable = false)
	private byte[] salt;

	@Column(nullable = false, length = 120)
	private String name;

	@OneToMany(mappedBy = "account")
	private List<Operation> operations;

	@Fetch(FetchMode.SUBSELECT)
	@ElementCollection(fetch=FetchType.EAGER,targetClass = Role.class)
	@CollectionTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id") )
	@Column(name = "role", length = 15)
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

	public Account() {
		roles = new HashSet<>();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPasswordHash() {
		return passwordHash;
	}

	public void setPassword(byte[] pass) {
		this.passwordHash = pass;
	}

	public byte[] getSalt() {
		return this.salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role user) {
		this.roles.add(user);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(email).append(passwordHash).append(name).toHashCode();
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
				.append(getPasswordHash(), other.getPasswordHash()).append(getName(), other.getName()).isEquals();
	}

}
