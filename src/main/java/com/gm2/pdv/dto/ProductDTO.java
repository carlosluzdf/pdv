package com.gm2.pdv.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private long id;
	@NotBlank(message = "O campo descrição é obrigatório!")
	private String description;
	@NotNull(message = "O campo preço é obrigatório!")
	private BigDecimal price;
	@NotNull(message = "O campo quantidade é obrigatório!")
	@Min(5)
	private int quantity;

//	public Product getEntity() {
//		Product product = new Product();
//		product.setDescription(getDescription());
//		product.setQuantity(getQuantity());
//		product.setPrice(getPrice());
//
//		return product;
//	}
}
