package com.wcs.atelierauthentification.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wcs.atelierauthentification.dto.UserDto;
import com.wcs.atelierauthentification.model.User;
import com.wcs.atelierauthentification.repository.RoleRepository;
import com.wcs.atelierauthentification.repository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@PostMapping
	public void create(@Valid @RequestBody(required = true) UserDto userDto) {
		//on vérifie si le username et l'email n'existe pas déjà
		if(userRepository.existsByUsername(userDto.getUsername()) ||
				userRepository.existsByEmail(userDto.getEmail())){
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
		}
		
		User user = new User();
		user.setUsername(userDto.getUsername());
		
	}
}
