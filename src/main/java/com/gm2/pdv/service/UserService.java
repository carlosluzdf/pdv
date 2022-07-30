package com.gm2.pdv.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gm2.pdv.dto.UserDTO;
import com.gm2.pdv.entity.User;
import com.gm2.pdv.exceptions.NoItemException;
import com.gm2.pdv.repository.UserRepository;
import com.gm2.pdv.security.SecurityConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepository;

	private ModelMapper mapper = new ModelMapper();

	public List<UserDTO> getAll(){
		List<UserDTO> usersDTO = new ArrayList<>();

		List<User> users = userRepository.findAll();
		for (User user : users) {
			usersDTO.add(getDadosUser(user));
		}

		return usersDTO;
	}

	public UserDTO getById(long id){
		User user = userRepository.findById(id).orElseThrow(() -> new NoItemException("Usuário não encontrado"));

		return getDadosUser(user);
	}

	public User getByUserName(String username){
		
		User user = userRepository.findByUsername(username);
		return user;
	}

	private UserDTO getDadosUser(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setUsername(user.getUsername());
//		userDTO.setPassword(user.getPassword());
		userDTO.setEnable(user.isEnabled());

		return userDTO;
	}

	@Transactional
	public long insert( UserDTO obj ) {

		User user = mapper.map(obj, User.class);
		user.setEnabled(true);
		user.setUsername(obj.getUsername());
		user.setPassword(SecurityConfig.passwordEncoder().encode(obj.getPassword()));

		return userRepository.save(user).getId();
	}

	@Transactional
	public long update( UserDTO obj ) {
		userRepository.findById(obj.getId()).orElseThrow(() -> new NoItemException("Usuário não encontrado"));

		User user = mapper.map(obj, User.class);
		user.setUsername(obj.getUsername());
		user.setPassword(SecurityConfig.passwordEncoder().encode(obj.getPassword()));

		return userRepository.save(user).getId();
	}
}
