package com.gm2.pdv.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private long id;
	@NotBlank(message = "O nome do usuário é obrigatório!")
	private String name;
	@NotBlank(message = "O login do usuário é obrigatório!")
	private String username;
	@NotBlank(message = "A senha do usuário é obrigatório!")
	private String password;
	private boolean isEnable;
}
