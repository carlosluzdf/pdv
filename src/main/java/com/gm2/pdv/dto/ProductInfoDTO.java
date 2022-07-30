package com.gm2.pdv.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInfoDTO {

	private long id;
	private String description;
	private int quantity;
	private BigDecimal price;
	private BigDecimal totalPrice;
}
