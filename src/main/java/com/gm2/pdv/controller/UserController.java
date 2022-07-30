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

import com.gm2.pdv.dto.ResponseDTO;
import com.gm2.pdv.dto.UserDTO;
import com.gm2.pdv.entity.User;
import com.gm2.pdv.exceptions.InvalidOperationException;
import com.gm2.pdv.exceptions.NoItemException;
import com.gm2.pdv.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity getAll() {
		return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity getById(@Valid @PathVariable long id) {
		try {
			return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	public ResponseEntity insert(@Valid @RequestBody UserDTO user) {
		try {
			return new ResponseEntity<>(userService.insert(user), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity update(@Valid @RequestBody UserDTO user) {
		try {
			return new ResponseEntity<>(userService.update(user), HttpStatus.OK);

		} catch (NoItemException e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (InvalidOperationException e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity delete(@PathVariable long id) {
//		try {
//			userRepository.deleteById(id);
//			return new ResponseEntity<>("Usuário removido com sucesso!", HttpStatus.OK);
//
//		} catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

}
