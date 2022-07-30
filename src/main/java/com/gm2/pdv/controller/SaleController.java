package com.gm2.pdv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gm2.pdv.dto.ResponseDTO;
import com.gm2.pdv.dto.SaleDTO;
import com.gm2.pdv.service.SaleService;

@Controller
@RequestMapping("/sale")
public class SaleController {

	@Autowired
	private SaleService saleService;

	@PostMapping
	public ResponseEntity insert(@RequestBody SaleDTO saleDTO){
		try {
			long id = saleService.save(saleDTO);
			return new ResponseEntity<>(id, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity getAll(){
		return new ResponseEntity<>( saleService.getAll(), HttpStatus.OK );
	}

	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable Long id){
		try {
			return new ResponseEntity<>(saleService.getById(id), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
