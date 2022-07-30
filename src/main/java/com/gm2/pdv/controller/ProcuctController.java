package com.gm2.pdv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gm2.pdv.dto.ProductDTO;
import com.gm2.pdv.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProcuctController {

	@Autowired
	ProductService productService;

	@GetMapping
	public ResponseEntity getAll() {
		return new ResponseEntity<>( productService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity getById(@Valid @PathVariable Long id) {
		return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity insert(@Valid @RequestBody ProductDTO product) {
		try {
			return new ResponseEntity<>(productService.insert(product), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity update(@Valid @RequestBody ProductDTO product) {

		try {
			return new ResponseEntity<>(productService.update(product), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@PutMapping("/delete/{id}")
//	public ResponseEntity delete(@PathVariable Long id) {
//
//		try {
//			Optional<Product> productToEdit = productRepositoty.findById(id);
//
////			delete lógico, porem ainda está sem o campo...
//			if( productToEdit.isPresent() ) {
////				productToEdit.setStatus(0);
//
////				productRepositoty.save(product);
////				return new ResponseEntity<>(product, HttpStatus.OK);
//
//				return new ResponseEntity<>( productRepositoty.save(productToEdit.get()), HttpStatus.OK);
//
//			}else {
//				return ResponseEntity.notFound().build();
//			}
//
//		} catch (Exception e) {
//			return new ResponseEntity<>( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
}
