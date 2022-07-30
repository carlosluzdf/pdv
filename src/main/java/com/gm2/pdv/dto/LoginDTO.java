package com.gm2.pdv.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

	@NotBlank(message = "O campo usuário é obrigatório")
	private String username;
	@NotBlank(message = "O campo senha é obrigatório")
	private String password;
}
