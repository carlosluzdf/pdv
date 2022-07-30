package com.gm2.pdv.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gm2.pdv.dto.ResponseDTO;
import com.gm2.pdv.service.CustomUserDetailService;
import com.gm2.pdv.service.JwtService;

public class JwtAuthFilter extends OncePerRequestFilter{

	private JwtService jwtService;
	private CustomUserDetailService userDetailService;

	public JwtAuthFilter( JwtService jwtService, CustomUserDetailService userDetailService ) {
		this.jwtService =  jwtService;
		this.userDetailService=  userDetailService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {

		try {
			String authorization = request.getHeader("Authorization");

			if( authorization != null && authorization.startsWith("Bearer") ) {
				String token = authorization.split(" ")[1];
				String username = jwtService.getUserName(token);

				UserDetails user = userDetailService.loadUserByUsername(username);

//				cria um usuário que será inserido com contesto do spring security
				UsernamePasswordAuthenticationToken userCtx = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

//				configurando o spring security como autenticação web
				userCtx.setDetails( new WebAuthenticationDetailsSource()
						.buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(userCtx);
			}

			filterChain.doFilter(request, response);

		} catch (RuntimeException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(convertObjectJson(new ResponseDTO("Token inválido!")));
		}
	}

	private String convertObjectJson(ResponseDTO responseDTO) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		return mapper.writeValueAsString(responseDTO);
	}

}
