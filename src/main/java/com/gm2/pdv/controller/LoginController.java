package com.gm2.pdv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gm2.pdv.dto.LoginDTO;
import com.gm2.pdv.dto.ResponseDTO;
import com.gm2.pdv.dto.TokenDTO;
import com.gm2.pdv.service.CustomUserDetailService;
import com.gm2.pdv.service.JwtService;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	CustomUserDetailService userDetailService;

	@Autowired
	JwtService jwtService;

	@Value("${security.jwt.expiration}")
	private String validity;

	@PostMapping
	public ResponseEntity login(@Valid @RequestBody LoginDTO loginDTO) {
		try {
//			verificar se as credenciais são válidas
			userDetailService.verifyUserCredentials(loginDTO);
//			geraro token
			String token = jwtService.generationToken(loginDTO.getUsername());

			return new ResponseEntity<>(new TokenDTO(token, validity), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
		}
	}
}
