package com.techlabs.app.service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techlabs.app.entity.User;
import com.techlabs.app.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

//	@Override
//	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
//		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
//				() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));
//
//		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
//
//		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//				Collections.singleton(authority));
//	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(usernameOrEmail).orElseThrow(
				() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRoles());
		Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Collections.singleton(authority));
	}

}
