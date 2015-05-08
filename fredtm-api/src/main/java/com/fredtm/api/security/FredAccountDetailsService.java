package com.fredtm.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fredtm.data.repository.AccountRepository;

public class FredAccountDetailsService implements UserDetailsService {

	@Autowired
	AccountRepository accountRepo;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {

	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return null;
	}

}
