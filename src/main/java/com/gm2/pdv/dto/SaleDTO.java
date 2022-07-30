package com.gm2.pdv.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaleDTO {

	private long userId;

	List<ProductDTO> items;
}
