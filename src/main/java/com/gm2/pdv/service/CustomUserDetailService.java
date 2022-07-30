package com.gm2.pdv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gm2.pdv.dto.LoginDTO;
import com.gm2.pdv.entity.User;
import com.gm2.pdv.exceptions.PasswordNotFoundException;
import com.gm2.pdv.security.SecurityConfig;
import com.gm2.pdv.security.UserPrincipal;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userService.getByUserName(username);
		if( user ==  null ) {
			throw new UsernameNotFoundException("Login inválido!");
		}

		return new UserPrincipal(user);
	}

	public void verifyUserCredentials(LoginDTO login) {
		UserDetails user = loadUserByUsername(login.getUsername());

		if( !SecurityConfig.passwordEncoder().matches(login.getPassword(), user.getPassword()) ) {
			throw new PasswordNotFoundException("Senha inválida!");
		}
	}
}
