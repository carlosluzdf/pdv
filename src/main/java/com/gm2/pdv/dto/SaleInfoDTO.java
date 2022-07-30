package com.gm2.pdv.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleInfoDTO {

	private long id;
	private String user;
	private String date;

	private List<ProductInfoDTO> products; 
}
