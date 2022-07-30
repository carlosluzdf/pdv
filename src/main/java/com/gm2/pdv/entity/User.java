package com.gm2.pdv.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "O nome do usuário é obrigatório!")
	private String name;

	@Column(length = 30, nullable = false, unique = true)
	@NotBlank(message = "O login do usuário é obrigatório!")
	private String username;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "A senha é obrigatória!")
	private String password;

	private boolean isEnabled;

//	nome do atributo que está configurado para User em Sale
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Sale> sales;

}
